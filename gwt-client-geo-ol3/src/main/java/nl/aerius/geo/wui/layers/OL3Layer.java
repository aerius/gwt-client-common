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
package nl.aerius.geo.wui.layers;

import java.util.Optional;

import ol.layer.Layer;

import nl.aerius.geo.domain.IsLayer;
import nl.aerius.geo.domain.LayerInfo;

public class OL3Layer implements IsLayer<Layer> {
  private final Layer layer;
  private final LayerInfo info;

  public OL3Layer(final Layer layer) {
    this(layer, null);
  }

  public OL3Layer(final Layer layer, final LayerInfo info) {
    if (layer == null) {
      throw new RuntimeException("Layer is null.");
    }

    this.layer = layer;
    this.info = info;
  }

  @Override
  public Layer asLayer() {
    return layer;
  }

  @Override
  public Optional<LayerInfo> getInfoOptional() {
    return Optional.of(info);
  }

  @Override
  public LayerInfo getInfo() {
    return info;
  }

  @Override
  public String toString() {
    return "OL3Layer " + (info == null ? "" : info.getTitle());
  }
}
