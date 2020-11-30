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
package nl.aerius.geo.epsg;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import nl.aerius.geo.BBox;
import nl.aerius.geo.domain.HexagonZoomLevel;

@Singleton
public class RDNewReceptorGridSettings extends ReceptorGridSettings {
  /**
   * Lowest X-coordinate for hexagon mapping of the Netherlands.
   */
  private static final double X_MIN = 3604;
  /**
   * Highest X-coordinate for hexagon mapping of the Netherlands.
   */
  private static final double X_MAX = 287959;

  /**
   * Lowest Y-coordinate for hexagon mapping of the Netherlands.
   */
  private static final double Y_MIN = 296800;
  /**
   * Highest Y-coordinate for hexagon mapping of the Netherlands.
   */
  private static final double Y_MAX = 629300;
  /**
   * The hexagon zoom level 1 for the a 100 * 100 square hexagon.
   *
   * This is specific for the Dutch hexagon grid, but SRM2 is only supported for the dutch situation.
   */
  public static final HexagonZoomLevel ZOOM_LEVEL_1 = new HexagonZoomLevel(1, 10000);

  /**
   * Total number of hexagons in a row.
   *
   * This is specific for the Dutch hexagon grid, but SRM2 is only supported for the dutch situation.
   */
  public static final int HEX_HOR = 1529;

  @Inject
  public RDNewReceptorGridSettings(final EPSG epsg) {
    super(getStaticBoundingBox(), epsg, HEX_HOR, getStaticZoomLevels());
  }

  private static BBox getStaticBoundingBox() {
    return new BBox(X_MIN, Y_MIN, X_MAX, Y_MAX);
  }

  private static List<HexagonZoomLevel> getStaticZoomLevels() {
    final ArrayList<HexagonZoomLevel> hexagonZoomLevels = new ArrayList<>();
    hexagonZoomLevels.add(ZOOM_LEVEL_1);
    return hexagonZoomLevels;
  }
}
