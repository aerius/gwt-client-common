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

import java.util.List;

import ol.Coordinate;
import ol.Extent;
import ol.OLFactory;
import ol.proj.Projection;
import ol.tilegrid.WmtsTileGrid;
import ol.tilegrid.WmtsTileGridOptions;

public final class OL3WmtsTileGridUtil {

  private OL3WmtsTileGridUtil() {
  }

  /**
   * Constructs a WmtsTileGrid from a scale list, origin and projection
   *
   * @param scaleList scale list (size determines number of zoomlevels)
   * @param origin origin of the tile grid
   * @param projection projection of the tile grid
   * @param extent extent of the tile grid
   * @return constructed WmtsTileGrid
   */
  public static WmtsTileGrid createWmtsTileGrid(final List<Double> scaleList, final Coordinate origin,
      final Projection projection, final Extent extent) {
    final WmtsTileGridOptions wmtsTileGridOptions = OLFactory.createOptions();

    final double[] resolutions = new double[scaleList.size()];
    final String[] matrixIds = new String[scaleList.size()];

    for (int i = 0; i < scaleList.size(); i++) {
      matrixIds[i] = String.valueOf(i);
      resolutions[i] = OL3GeometryUtil.scaleToResolution(scaleList.get(i), projection);
    }

    wmtsTileGridOptions.setOrigin(origin);
    wmtsTileGridOptions.setResolutions(resolutions);
    wmtsTileGridOptions.setMatrixIds(matrixIds);
    wmtsTileGridOptions.setExtent(extent);

    return new WmtsTileGrid(wmtsTileGridOptions);
  }

}
