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

import java.util.Arrays;
import java.util.List;

import ol.Coordinate;
import ol.proj.Projection;
import ol.tilegrid.WmtsTileGrid;

public final class TM75WmtsTileGrid {

  private static final List<Double> RESOLUTIONS = Arrays.asList(
      1889884.7321501793,
      1417413.5491126343,
      1039436.6026825986,
      519718.3013412993,
      226786.1678580215,
      113393.08392901075,
      56696.54196450538,
      28348.27098225269,
      18898.84732150179,
      9449.423660750896,
      7559.538928600717,
      4724.711830375448,
      2362.355915187724,
      1181.177957593862,
      472.4711830375448);

  private static final Coordinate ORIGIN = new Coordinate(-5422600.0, 4321500.0);

  /**
   * TileGrid supports both TM65 and TM75 since the projection code is the same
   */
  public static final List<String> PROJECTION = Arrays.asList("EPSG:29902", "EPSG:29903");

  private TM75WmtsTileGrid() {
  }

  public static WmtsTileGrid getTileGrid() {
    return OL3WmtsTileGridUtil.createWmtsTileGrid(RESOLUTIONS, ORIGIN, Projection.get(PROJECTION.get(0)));
  }
}
