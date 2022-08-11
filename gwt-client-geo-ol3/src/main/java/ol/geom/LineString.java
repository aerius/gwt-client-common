/*******************************************************************************
 * Copyright 2014, 2016 gwt-ol3
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package ol.geom;

import ol.Coordinate;

import jsinterop.annotations.JsType;

/**
 * Linestring geometry.
 *
 * @author sbaumhekel
 */
@JsType(isNative = true)
public class LineString extends SimpleGeometryCoordinates {

  public LineString(Coordinate[] coordinates) {}

  public LineString(Coordinate[] coordinates, String geometryLayout) {}

  /**
   * Append the passed coordinate to the coordinates of the linestring.
   *
   * @param coordinate
   *            Coordinate.
   */
  public native void appendCoordinate(Coordinate coordinate);

  /**
   * Return the length of the linestring on projected plane.
   *
   * @return length (on projected plane).
   */
  public native double getLength();

  /**
   * Return the coordinate at the provided fraction along the linestring. The fraction is a number between 0 and 1, where 0 is the start of the linestring and 1 is the end.
   *
   * AERIUS FIX
   *
   * @param fraction Fraction.
   * @param opt_dest Optional coordinate whose values will be modified. If not provided, a new coordinate will be returned.
   * @return Coordinate of the interpolated point.
   */
  public native Coordinate getCoordinateAt(double fraction, Coordinate opt_dest);

}
