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
package nl.overheid.aerius.geo.domain.layer;

import java.util.stream.Stream;

public enum LayerType {
  WMS("wms"),
  WMTS("wmts");

  private final String name;

  private LayerType(final String name) {
    this.name = name;
  }

  public static LayerType fromName(final String name) {
    return Stream.of(values())
        .filter(v -> v.getName().equals(name))
        .findFirst().orElseThrow(() -> new NullPointerException("No LayerType for name"));
  }

  public String getName() {
    return name;
  }
}
