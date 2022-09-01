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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import ol.Coordinate;
import ol.Extent;
import ol.format.Wkt;
import ol.format.WktWriteOptions;
import ol.geom.Circle;
import ol.geom.Geometry;
import ol.geom.LineString;
import ol.geom.Point;
import ol.geom.Polygon;

import nl.aerius.shared.geo.BBox;

public final class OL3GeometryUtil {

  public static final int WKT_DECIMALS = 2;

  private static final Wkt WKT = new Wkt();

  private OL3GeometryUtil() {}

  public static BBox bboxFromExtent(final Extent extent) {
    return new BBox(extent.getLowerLeftX(), extent.getLowerLeftY(), extent.getUpperRightX(), extent.getUpperRightY());
  }

  /**
   * Returns the midpoint of certain geometry. Operates on OL Geometry objects
   *
   * @param geometry Geometry to determine the midpoint for
   * @return midpoint
   */
  public static Point getMiddlePointOfGeometry(final Geometry geometry) {
    if (geometry instanceof LineString) {
      // LineString: take the middle
      return new Point(((LineString) geometry).getCoordinateAt(0.5, null));
    } else if (geometry instanceof Polygon) {
      // Polygon: take the interior point
      return ((Polygon) geometry).getInteriorPoint();
    } else if (geometry instanceof Point) {
      // Point is the point
      return (Point) geometry;
    } else if (geometry instanceof Circle) {
      // Circle, take the center
      return new Point(((Circle) geometry).getCenter());
    }
    return null;
  }

  /**
   * Returns the vertices in a certain geometry.
   *
   * @param geometry Geometry to determine the vertices for
   * @return vertices
   */
  public static Coordinate[] getCoordinatesOfGeometry(final Geometry geometry) {
    if (geometry instanceof LineString) {

      return ((LineString) geometry).getCoordinates();

    } else if (geometry instanceof Polygon) {

      return ((Polygon) geometry).getCoordinates()[0];

    } else if (geometry instanceof Point) {

      return new Coordinate[] { ((Point) geometry).getCoordinates() };
    } else if (geometry instanceof Circle) {

      return new Coordinate[] { ((Circle) geometry).getCenter() };
    }
    return null;
  }

  public static String toWktString(final Geometry geometry) {
    final WktWriteOptions wktWriteOptions = new WktWriteOptions();
    wktWriteOptions.setDecimals(WKT_DECIMALS);

    return WKT.writeGeometry(geometry, wktWriteOptions);
  }

  /**
   * Construct an offset from a line string
   *
   * @param lineString base geometry
   * @param distance   distance from the linestring
   * @return a linestring from a certain distance, rendered at the righthand side
   *         of the geometry
   *
   *         NOTE: if you need a lefthand side offset of the geometry, use a
   *         negative distance
   */
  public static LineString offsetFromLineString(final LineString lineString, final double distance) {

    final Coordinate[] oldCoords = lineString.getCoordinates();
    final List<Coordinate> newCoords = new ArrayList<>();

    newCoords.add(offsetCoordinate(oldCoords[0], getDirectionOfSegment(oldCoords[0], oldCoords[1]), distance));
    for (int i = 1; i < oldCoords.length - 1; i++) {
      final double angle1 = getDirectionOfSegment(oldCoords[i - 1], oldCoords[i]);
      final double angle2 = getDirectionOfSegment(oldCoords[i], oldCoords[i + 1]);
      final double angle = (angle1 + angle2) / 2.0;
      newCoords.add(offsetCoordinate(oldCoords[i], angle, distance / Math.cos(Math.abs(angle - angle1))));
    }
    newCoords.add(offsetCoordinate(oldCoords[oldCoords.length - 1],
        getDirectionOfSegment(oldCoords[oldCoords.length - 2], oldCoords[oldCoords.length - 1]), distance));

    return new LineString(newCoords.toArray(new Coordinate[0]));
  }

  /**
   * Returns an outline polygon from a certain distance from a linestring
   *
   * @param lineString base geometry
   * @param distance   distance
   * @return return outline polygon
   */
  public static Polygon outlinePolygonOfLineString(final LineString lineString, final double distance) {
    final List<Coordinate> lineStringRightSide = Arrays.asList(offsetFromLineString(lineString, distance).getCoordinates());
    final List<Coordinate> lineStringLeftSide = Arrays.asList(offsetFromLineString(lineString, -distance).getCoordinates());
    Collections.reverse(lineStringLeftSide);
    final Coordinate[][] coordinates = { Stream.concat(lineStringRightSide.stream(), lineStringLeftSide.stream()).toArray(Coordinate[]::new) };
    return new Polygon(coordinates);
  }

  /**
   * Returns the direction of a certain line segment
   *
   * @param from first coordinate
   * @param to   second coordinate
   * @return direction as an angle
   */
  public static double getDirectionOfSegment(final Coordinate from, final Coordinate to) {
    return Math.atan2(to.getY() - from.getY(), to.getX() - from.getX());
  }

  private static Coordinate offsetCoordinate(final Coordinate original, final double direction, final double distance) {
    return new Coordinate(
        Math.sin(direction) * distance + original.getX(),
        -Math.cos(direction) * distance + original.getY());
  }

}
