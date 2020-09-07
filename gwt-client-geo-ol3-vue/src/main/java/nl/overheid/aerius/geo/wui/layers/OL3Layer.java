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
package nl.overheid.aerius.geo.wui.layers;

import java.util.Optional;

import ol.layer.Layer;

import nl.overheid.aerius.geo.domain.IsLayer;
import nl.overheid.aerius.geo.domain.LayerInfo;

public class OL3Layer implements IsLayer<Layer> {
  private final Layer layer;
  private final LayerInfo info;
  private final Runnable retirement;

  public OL3Layer(final Layer layer) {
    this(layer, null, null);
  }

  public OL3Layer(final Layer layer, final LayerInfo info) {
    this(layer, info, null);
  }

  public OL3Layer(final Layer layer, final LayerInfo info, final Runnable retirement) {
    if (layer == null) {
      throw new RuntimeException("Layer is null.");
    }

    this.layer = layer;
    this.info = info;
    this.retirement = retirement;
  }

  @Override
  public Layer asLayer() {
    return layer;
  }

  @Override
  public Optional<LayerInfo> getInfo() {
    return Optional.of(info);
  }

  public void retire() {
    if (retirement != null) {
      retirement.run();
    }
  }

  @Override
  public String toString() {
    return "OL3Layer " + (info == null ? "" : info.getTitle()) + "]";
  }
}
