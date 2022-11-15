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
package nl.aerius.geo.domain.legend;

public enum LegendType {

  CIRCLE(true),
  CIRCLE_IN_HEXAGON(true),
  HEXAGON(true),
  LINE(true),
  LINEDASH(true),
  ROAD(true),
  TEXT(false);

  private final boolean colorLegend;

  LegendType(final boolean colorLegend) {
    this.colorLegend = colorLegend;
  }

  public boolean hasColorLegend() {
    return colorLegend;
  }

}
