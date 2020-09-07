/*
 * Copyright Dutch Ministry of Agriculture, Nature and Food Quality
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package nl.overheid.aerius.geo.wui.util;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.inject.Inject;

import ol.OLFactory;
import ol.layer.Image;
import ol.layer.Layer;
import ol.layer.LayerOptions;
import ol.source.ImageWms;
import ol.source.ImageWmsOptions;
import ol.source.ImageWmsParams;

import nl.overheid.aerius.geo.domain.IsLayer;
import nl.overheid.aerius.geo.domain.LayerInfo;
import nl.overheid.aerius.geo.domain.layer.LayerConfig;
import nl.overheid.aerius.geo.domain.layer.WMSLayerConfig;
import nl.overheid.aerius.geo.util.MapLayerFactory;
import nl.overheid.aerius.geo.wui.layers.OL3Layer;
import nl.overheid.aerius.wui.dev.GWTProd;
import nl.overheid.aerius.wui.replacing.ActivatorAssistant;
import nl.overheid.aerius.wui.replacing.ObservingReplacementAssistant;
import nl.overheid.aerius.wui.replacing.ReplacementRegistration;

public final class OL3MapLayerFactory implements MapLayerFactory<Layer> {
  private final ObservingReplacementAssistant observableReplacer;

  private final ActivatorAssistant activatorAssistant;

  @Inject
  public OL3MapLayerFactory(final ObservingReplacementAssistant observableReplacer, final ActivatorAssistant activatorAssistant) {
    this.observableReplacer = observableReplacer;
    this.activatorAssistant = activatorAssistant;
  }

  public static IsLayer<Layer> wrap(final Layer layer) {
    return new OL3Layer(layer);
  }

  public static IsLayer<Layer> wrap(final Layer layer, final LayerInfo info, final Runnable retirement) {
    return new OL3Layer(layer, info, retirement);
  }

  @Override
  public IsLayer<Layer> prepareLayer(final LayerConfig c) {
    IsLayer<Layer> layer;

    switch (c.getType()) {
    case WMS:
      layer = prepareWMSLayer((WMSLayerConfig) c);
      break;
    default:
      throw new RuntimeException("Untyped LayerConfig encountered. " + c);
    }

    layer.getInfo().ifPresent(v -> v.setSelectables(c.getSelectables()));

    return layer;
  }

  public IsLayer<Layer> prepareWMSLayer(final WMSLayerConfig c) {
    final ImageWmsParams imageWMSParams = OLFactory.createOptions();

    imageWMSParams.setLayers(c.getLayer());
    imageWMSParams.setFormat(c.getFormat());

    final ImageWmsOptions imageWMSOptions = OLFactory.createOptions();
    imageWMSOptions.setUrl(c.getUrl());
    imageWMSOptions.setParams(imageWMSParams);

    final ImageWms imageWMSSource = new ImageWms(imageWMSOptions);

    final LayerOptions layerOptions = OLFactory.createOptions();
    layerOptions.setSource(imageWMSSource);

    final Image wmsLayer = new Image(layerOptions);
    wmsLayer.setOpacity(c.getOpacity());
    wmsLayer.setVisible(c.isVisible());

    final Optional<ReplacementRegistration> paramRegister = c.getViewParams()
        .map(params -> observableReplacer.registerStrict(params, v -> {
          if (v != null) {
            GWTProd.warn("MAP", "Updating viewparams: " + v);
            imageWMSParams.setViewParams(v);
            imageWMSSource.updateParams(imageWMSParams);
          } else {
            GWTProd.warn("MAP", "Could not update view params because of missing parameters: " + c.getViewParams().orElse(""));
          }
        }));

    final Optional<ReplacementRegistration> layerRegister = c.getActivators()
        .map(activators -> activatorAssistant.register(activators, active -> {
          final String layers = Stream.concat(active.stream().map(v -> v.getLayer().orElse(c.getLayer())),
              Stream.of(c.getLayer()))
              .collect(Collectors.joining(","));

          final String styles = Stream.concat(active.stream().map(v -> v.getStyle()),
              Stream.of(c.getStyle()))
              .collect(Collectors.joining(","));

          imageWMSParams.setLayers(layers);
          imageWMSParams.setStyles(styles);
          imageWMSSource.updateParams(imageWMSParams);
        }));

    final LayerInfo info = new LayerInfo();
    info.setLegendConfig(c.getLegend());
    info.setName(c.getName());
    info.setTitle(c.getTitle());
    info.setBundle(c.getBundleName());
    info.setCluster(c.getClusterName());
    info.setFriendlyName(c.getFriendlyName());

    return wrap(wmsLayer, info, () -> {
      layerRegister.ifPresent(ReplacementRegistration::unregister);
      paramRegister.ifPresent(ReplacementRegistration::unregister);
    });
  }
};
