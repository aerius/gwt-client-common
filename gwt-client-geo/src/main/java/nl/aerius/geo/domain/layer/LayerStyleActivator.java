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
package nl.aerius.geo.domain.layer;

import java.util.Optional;

import nl.aerius.wui.replacing.IsActivatorInfo;

public class LayerStyleActivator implements IsActivatorInfo {
  private final String name;

  private final String layer;
  private final String style;

  public LayerStyleActivator(final String name, final String layer, final String style) {
    this.name = name;
    this.layer = layer;
    this.style = style;
  }

  public String getStyle() {
    return style;
  }

  public Optional<String> getLayer() {
    return Optional.ofNullable(layer);
  }

  public static LayerStyleActivator create(final String name, final String layer, final String style) {
    return new LayerStyleActivator(name, layer, style);
  }

  @Override
  public String getName() {
    return name;
  }
}
