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

import java.util.List;

import com.google.inject.Inject;

import nl.overheid.aerius.geo.BBox;
import nl.overheid.aerius.geo.domain.HexagonZoomLevel;
import nl.overheid.aerius.geo.domain.Point;
import nl.overheid.aerius.geo.domain.ReceptorPoint;
import nl.overheid.aerius.geo.epsg.ReceptorGridSettings;

/**
 * Utility class for Receptors.
 */
public final class ReceptorUtil {
  private static final int TRIPLE = 3;
  private static final double ONE_AND_HALF = 1.5;

  private static final int INDEX_X = 0;
  private static final int INDEX_Y = 1;

  // Bounds in which receptors exist and in which receptor id/point calculations are guaranteed to succeed.
  private final double xMinGuarantee;
  private final double xMaxGuarantee;
  private final double yMinGuarantee;
  private final double yMaxGuarantee;

  private final double doubleHexRow;
  private final double tripleRadius;
  private final double oneAndHalfRadius;
  private final double halfHeight;

  private final HexagonZoomLevel zoomLevel1;
  private final BBox boundingBox;
  private final int hexHor;

  private final double[][] zoomLevelXY;

  private final ReceptorGridSettings rgs;

  @Inject
  public ReceptorUtil(final ReceptorGridSettings rgs) {
    this.rgs = rgs;

    this.boundingBox = rgs.getBoundingBox();
    this.hexHor = rgs.getHexHor();
    this.zoomLevel1 = rgs.getZoomLevel1();
    tripleRadius = zoomLevel1.getHexagonRadius() * TRIPLE;
    xMinGuarantee = boundingBox.getMinX();
    xMaxGuarantee = boundingBox.getMaxX() + tripleRadius;
    yMinGuarantee = boundingBox.getMinY();
    yMaxGuarantee = boundingBox.getMaxY() + zoomLevel1.getHexagonHeight();

    doubleHexRow = hexHor * 2;
    oneAndHalfRadius = zoomLevel1.getHexagonRadius() * ONE_AND_HALF;
    halfHeight = zoomLevel1.getHexagonHeight() / 2;

    final List<HexagonZoomLevel> zoomLevels = rgs.getHexagonZoomLevels();
    final int size = zoomLevels.size();
    zoomLevelXY = new double[size][2];
    for (final HexagonZoomLevel hzl : zoomLevels) {
      final int level = hzl.getLevel() - 1;
      zoomLevelXY[level] = new double[2];
      zoomLevelXY[level][INDEX_X] = hzl.getHexagonRadius();
      zoomLevelXY[level][INDEX_Y] = hzl.getHexagonHeight();
    }
  }

  /**
   * Set the receptor X and Y coordinates given the id.
   *
   * NOTE2: Also remove the use of HashMap in CalculationResult (replace it with a Set or List)
   *
   * @param rp
   *          AeriusPoint to set X and Y coordinates for
   * @return AeriusPoint with X and Y coordinates
   **/
  public ReceptorPoint createReceptorPointFromId(final int id) {
    final int dx = (id - 1) % hexHor;
    final int dy = (id - 1) / hexHor;
    final double sndrow = (id - 1) % doubleHexRow >= hexHor ? oneAndHalfRadius : 0;

    final double x = dx * tripleRadius + sndrow + boundingBox.getMinX();
    final double y = dy * halfHeight + boundingBox.getMinY();

    return new ReceptorPoint(id, x, y);
  }

  /**
   * Sets the receptor id based on the given X and Y coordinates. Only works for X and Y within NL bounding box.
   *
   * @param rec
   *          ReceptorPoint to set id for
   * @return ReceptorPoint with id
   */
  public ReceptorPoint createReceptorIdFromPoint(final Point rec) {
    return createReceptorIdFromPoint(rec, zoomLevel1);
  }

  /**
   * Calculates the receptor id based on the x/y and zoomlevel?. Only works for X and Y within NL bounding box.
   *
   * @param rp
   *          point to set id on
   * @param zl
   *          zoomlevel the id is on
   * @return reference to input point
   */
  private ReceptorPoint createReceptorIdFromPoint(final Point rp, final HexagonZoomLevel zl) {
    final int id = getReceptorIdFromCoordinate(rp.getX(), rp.getY(), zl);

    return createReceptorPointFromId(id);
  }

  /**
   * Calculates the receptor id based on the x/y and zoomlevel?. Only works for X and Y within NL bounding box.
   *
   * @param _x The x coordinate for the point.
   * @param _y The y coordinate for the point.
   * @param zl zoomlevel the id is on
   * @return The receptor id for the coordinate.
   */
  public int getReceptorIdFromCoordinate(final double _x, final double _y, final HexagonZoomLevel zl) {
    // Get x/y values
    final double x = correctPositionX(_x);
    final double y = correctPositionY(_y);

    // Hex config
    // Note: 3 * hex_radius == the (horizontal) distance between 2 hexagons on the same horizontal axis
    // 1 1/2 * hex_radius == distance to the left/right-most point.
    // hex_height == distance between even rows or odd rows
    // half height == distance between even and odd row.
    final double tripleHexRadius = zl.getHexagonRadius() * 3;
    final double oneAndHalfRadius = tripleHexRadius / 2;
    final double hexHeight = zl.getHexagonHeight();
    final double halfHeight = hexHeight / 2;

    // Horizontal offset
    final double xEven = x - boundingBox.getMinX();
    final double xOdd = xEven + oneAndHalfRadius;

    // Vertical offset
    final double yEven = y - boundingBox.getMinY();
    final double yOdd = yEven + halfHeight;

    final double xEvenMinusOneAndHalfRadius = xEven - oneAndHalfRadius;
    final double xEvenDividedByTripleRadius = xEven / tripleHexRadius;

    final double yEvenMinusHalfHeight = yEven - halfHeight;
    final double yEvenDividedByHexagonHeight = yEven / hexHeight;

    // Horizontal distance
    final double horDistToEven = xEvenMinusOneAndHalfRadius - (long) xEvenDividedByTripleRadius * tripleHexRadius;
    final double horDistToOdd = Math.abs(xEvenMinusOneAndHalfRadius - (long) (xEvenMinusOneAndHalfRadius / tripleHexRadius) * tripleHexRadius)
        - oneAndHalfRadius;

    // Vertical distance
    final double vertDistToEven = yEvenMinusHalfHeight - (long) yEvenDividedByHexagonHeight * hexHeight;
    final double vertDistToOdd = Math.abs(yEvenMinusHalfHeight - (long) (yEvenMinusHalfHeight / hexHeight) * hexHeight) - halfHeight;

    // Pyth distance
    final double distToEvenGrid = horDistToEven * horDistToEven + vertDistToEven * vertDistToEven;
    final double distToOddGrid = horDistToOdd * horDistToOdd + vertDistToOdd * vertDistToOdd;

    // Declare receptor id var
    final int recId;

    // Do the test
    if (distToEvenGrid >= distToOddGrid) {
      recId = hexHor * 2 * (int) (yOdd / hexHeight) + (int) (xOdd / tripleHexRadius) + 1;
    } else {
      recId = hexHor * (2 * (int) yEvenDividedByHexagonHeight + 1) + (int) xEvenDividedByTripleRadius + 1;
    }

    return recId;
  }

  private double correctPositionX(final double x) {
    return Math.min(Math.max(x, xMinGuarantee), xMaxGuarantee);
  }

  private double correctPositionY(final double y) {
    return Math.min(Math.max(y, yMinGuarantee), yMaxGuarantee);
  }

  public boolean isPointWithinBoundingBox(final Point point) {
    return point.getX() >= rgs.getBoundingBox().getMinX() && point.getX() <= rgs.getBoundingBox().getMaxX()
        && point.getY() >= rgs.getBoundingBox().getMinY() && point.getY() <= rgs.getBoundingBox().getMaxY();
  }
}