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
package nl.overheid.aerius.geo.util;

import nl.overheid.aerius.geo.domain.HexagonZoomLevel;
import nl.overheid.aerius.geo.domain.Point;
import nl.overheid.aerius.wui.util.MathUtil;

/**
 * Utility class for hexagons.
 */
public final class HexagonUtil {

  private HexagonUtil() {}

  /**
   * Returns a Geometry with a hexagon conforming to the given Point and HexagonConfiguration. The points of the hexagon are given in the following
   * order:
   *
   * <pre>
   *    6 - 1
   *   /     \
   *  5   x   2
   *   \     /
   *    4 - 3
   *
   *  Where x is the point given as argument.
   * </pre>
   *
   * @param point Center of the hexagon
   * @param config the hexagon zoom level for level 1
   * @return Returns the hexagon as POLYGON string
   */
  public static String createHexagonWkt(final Point point, final HexagonZoomLevel config) {
    // Store hexagon values
    final double[] horizontal = config.getHorizontal();
    final double[] vertical = config.getVertical();
    // Format hexagon into a wkt string and wrap it in a Geometry
    final StringBuilder builder = new StringBuilder("POLYGON ((");
    // Iterate over the number of corners in a hexagon
    for (int i = 0; i < 6; i++) {
      // Add the x/y values
      builder.append(MathUtil.round(point.getX() + horizontal[i]));
      builder.append(' ');
      builder.append(MathUtil.round(point.getY() + vertical[i]));
      builder.append(',');
    }

    // Polygon first and last point need to be the same
    builder.append(MathUtil.round(point.getX() + horizontal[0]));
    builder.append(' ');
    builder.append(MathUtil.round(point.getY() + vertical[0]));

    builder.append("))");

    return builder.toString();
  }
}