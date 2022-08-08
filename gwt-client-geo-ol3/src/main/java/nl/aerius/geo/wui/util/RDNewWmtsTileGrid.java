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

public final class RDNewWmtsTileGrid {

  private static final List<Double> RESOLUTIONS = Arrays.asList(
      12288000.0,
      6144000.0,
      3072000.0,
      1536000.0,
      768000.0,
      384000.0,
      192000.0,
      96000.0,
      48000.0,
      24000.0,
      12000.0,
      6000.0,
      3000.0,
      1500.0,
      750.0);

  private static final Coordinate ORIGIN = new Coordinate(-285401.92, 903401.92);

  public static final List<String> PROJECTIONS = Arrays.asList("EPSG:28992");

  private RDNewWmtsTileGrid() {
  }

  public static WmtsTileGrid getTileGrid() {
    return OL3WmtsTileGridUtil.createWmtsTileGrid(RESOLUTIONS, ORIGIN, Projection.get(PROJECTIONS.get(0)));
  }
}
