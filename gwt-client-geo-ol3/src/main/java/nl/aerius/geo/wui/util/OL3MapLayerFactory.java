/*
 * Copyright the State of the Netherlands
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
package nl.aerius.geo.wui.util;

import java.util.function.Consumer;

import ol.OLFactory;
import ol.layer.Image;
import ol.layer.Layer;
import ol.layer.LayerOptions;
import ol.layer.Tile;
import ol.layer.TileLayerOptions;
import ol.proj.Projection;
import ol.source.BingMaps;
import ol.source.BingMapsOptions;
import ol.source.ImageWms;
import ol.source.ImageWmsOptions;
import ol.source.ImageWmsParams;
import ol.source.Wmts;
import ol.source.WmtsOptions;
import ol.tilegrid.WmtsTileGrid;

import nl.aerius.geo.domain.IsLayer;
import nl.aerius.geo.domain.LayerInfo;
import nl.aerius.geo.shared.LayerBingProps;
import nl.aerius.geo.shared.LayerMultiWMSProps;
import nl.aerius.geo.shared.LayerProps;
import nl.aerius.geo.shared.LayerWMSProps;
import nl.aerius.geo.shared.LayerWMTSProps;
import nl.aerius.geo.util.MapLayerFactory;
import nl.aerius.geo.wui.layers.OL3Layer;
import nl.aerius.wui.dev.GWTProd;

public final class OL3MapLayerFactory implements MapLayerFactory<Layer> {
  private static final String DEFAULT = "default";
  private final Consumer<String> layerErrorHandler;

  public OL3MapLayerFactory(final Consumer<String> layerErrorHandler) {
    this.layerErrorHandler = layerErrorHandler;
  }

  public static IsLayer<Layer> wrap(final Layer layer) {
    return new OL3Layer(layer);
  }

  public static IsLayer<Layer> wrap(final Layer layer, final LayerInfo info) {
    return new OL3Layer(layer, info);
  }

  @Override
  public IsLayer<Layer> prepareLayer(final LayerProps c) {
    IsLayer<Layer> layer;
    if (c instanceof LayerMultiWMSProps) {
      layer = prepareMultiWMSLayer((LayerMultiWMSProps) c);
    } else if (c instanceof LayerWMSProps) {
      layer = prepareWMSLayer((LayerWMSProps) c);
    } else if (c instanceof LayerWMTSProps) {
      layer = prepareWMTSLayer((LayerWMTSProps) c);
    } else if (c instanceof LayerBingProps) {
      layer = prepareBingLayer((LayerBingProps) c);
    } else {
      throw new RuntimeException("Unsupported layer configuration: " + c.getClass().getSimpleName());
    }

    layer.asLayer().setOpacity(c.getOpacity());
    layer.asLayer().setVisible(c.isVisible());

    layer.getInfo().setTitle(c.getTitle());
    layer.getInfo().setName(c.getName());
    layer.getInfo().setBundle(c.getBundleName());

    final Projection projection = layer.asLayer().getSource().getProjection();
    if (c.getMinScale() != null && projection != null) {
      layer.asLayer().setMinResolution(OL3GeometryUtil.scaleToResolution(c.getMinScale(), projection));
    }
    if (c.getMaxScale() != null && projection != null) {
      layer.asLayer().setMaxResolution(OL3GeometryUtil.scaleToResolution(c.getMaxScale(), projection));
    }
    return layer;
  }

  private IsLayer<Layer> prepareBingLayer(final LayerBingProps conf) {
    final BingMapsOptions bingOptions = OLFactory.createOptions();
    bingOptions.setImagerySet(conf.getImagerySet());
    bingOptions.setKey(conf.getKey());

    final BingMaps bingSource = new BingMaps(bingOptions);

    final TileLayerOptions layerOptions = OLFactory.createOptions();
    layerOptions.setSource(bingSource);
    final Tile layer = new Tile(layerOptions);

    final LayerInfo info = new LayerInfo();
    info.setLegend(conf.getLegend());
    return wrap(layer, info);
  }

  public IsLayer<Layer> prepareMultiWMSLayer(final LayerMultiWMSProps conf) {
    final LayerInfo info = new LayerInfo();
    info.setLegend(conf.getLegend());

    final Image image = prepareWMSImage(conf);
    info.setOptions(OL3MultiWMSLayerUtil.getLayerOptions(conf, image));

    return wrap(image, info);
  }

  public IsLayer<Layer> prepareWMSLayer(final LayerWMSProps conf) {
    final LayerInfo info = new LayerInfo();
    info.setLegend(conf.getLegend());

    return wrap(prepareWMSImage(conf), info);
  }

  private Image prepareWMSImage(final LayerWMSProps conf) {

    final ImageWmsParams imageWMSParams = OLFactory.createOptions();
    imageWMSParams.setLayers(conf.getName());

    final ImageWmsOptions imageWMSOptions = OLFactory.createOptions();
    imageWMSOptions.setUrl(conf.getUrl());
    imageWMSOptions.setParams(imageWMSParams);

    final ImageWms imageWMSSource = new ImageWms(imageWMSOptions);
    imageWMSSource.once("imageloaderror", e -> {
      layerErrorHandler.accept(conf.getTitle());
    });

    final LayerOptions layerOptions = OLFactory.createOptions();
    layerOptions.setSource(imageWMSSource);

    final Image wmsLayer = new Image(layerOptions);

    return wmsLayer;
  }

  public IsLayer<Layer> prepareWMTSLayer(final LayerWMTSProps conf) {
    final WmtsOptions wmtsOptions = OLFactory.createOptions();

    wmtsOptions.setMatrixSet(conf.getTileMatrixSet());
    wmtsOptions.setUrl(conf.getUrl());
    wmtsOptions.setLayer(conf.getName());
    wmtsOptions.setFormat(conf.getType());

    final Projection projection = Projection.get(conf.getProjection());
    wmtsOptions.setProjection(projection);
    wmtsOptions.setWrapX(true);
    wmtsOptions.setTileGrid(createWmtsTileGrid(projection));
    wmtsOptions.setStyle(DEFAULT);

    final Wmts wmtsSource = new Wmts(wmtsOptions);

    final LayerOptions wmtsLayerOptions = OLFactory.createOptions();
    wmtsLayerOptions.setSource(wmtsSource);

    final Tile wmtsLayer = new Tile(wmtsLayerOptions);

    final LayerInfo info = new LayerInfo();
    return wrap(wmtsLayer, info);
  }

  /**
   * Creates a WMTS tilegrid.
   *
   * @param projection projection of the grid
   * @return WMTS tilegrid
   */
  private static WmtsTileGrid createWmtsTileGrid(final Projection projection) {
    if (TM75WmtsTileGrid.PROJECTION.contains(projection.getCode())) {
      return TM75WmtsTileGrid.getTileGrid();
    }
    if (RDNewWmtsTileGrid.PROJECTIONS.contains(projection.getCode())) {
      return RDNewWmtsTileGrid.getTileGrid();
    }

    GWTProd.warn("No WmtsTileGrid for unknown projection " + projection.getCode());
    return null;
  }
}
