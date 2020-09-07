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
package nl.overheid.aerius.geo.domain;

/**
 * The piece of ASCII genius below depicts a normal hexagon, with horizontal
 * top- and bottom-axes, and corners on the right and left. This format of a
 * hexagon is considered the 'normal hexagon'.
 *
 * <pre>
 *  |   r  |
 *     _________     __
 *    /       / \
 *   /      r/   \
 *  /      ./_____\  h
 *  \          r  /
 *   \  r/2      /
 *    \|___|____/    __
 *
 *   r = radius = half the width of a hexagon, and the length of a single
 *     hexagon rib.
 *   h = height
 * </pre>
 */
public class HexagonZoomLevel {
  public static final int HEXAGON_CORNERS = 6;

  // Maps the route of a single hexagon, starting from the most-right corner
  private static final double[] HORIZONTAL_HEXAGON_MODS = {0.5, 1.0, 0.5, -0.5, -1, -0.5 };
  private static final double[] VERTICAL_HEXAGON_MODS = {1.0, 0.0, -1.0, -1.0, 0.0, 1.0 };

  private int level;
  private int surfaceLevel1;
  private double hexagonSurface;
  private double hexagonRadius;
  private double hexagonHeight;
  private final double[] horizontal = new double[HEXAGON_CORNERS];
  private final double[] vertical = new double[HEXAGON_CORNERS];

  /**
   * Initializes a HexagonZoomLevel for the given level.
   * @param level the zoom level
   * @param surfaceLevelMin the surface in square meters for level 1
   */
  public HexagonZoomLevel(final int level, final int surfaceLevelMin) {
    this.level = level;
    this.surfaceLevel1 = surfaceLevelMin;
    hexagonSurface = calculateSurface(level, surfaceLevelMin);
    hexagonRadius = calculateRadius(hexagonSurface);
    hexagonHeight = calculateHexagonHeight(hexagonRadius);
    fillCorners(hexagonRadius, hexagonHeight);
  }

  public HexagonZoomLevel() {
    // needed for GWT.
  }

  @Override
  public boolean equals(final Object obj) {
    return obj != null && obj.getClass() == this.getClass() && level == ((HexagonZoomLevel) obj).getLevel();
  }

  @Override
  public int hashCode() {
    return level;
  }

  public int getLevel() {
    return level;
  }

  public int getSurfaceLevel1() {
    return surfaceLevel1;
  }

  /**
   * Returns the surface of a hexagon in square meters.
   *
   * @return surface in square meters
   */
  public double getHexagonSurface() {
    return hexagonSurface;
  }

  /**
   * Returns the height of a hexagon.
   *
   * @return height of hexagon in meters
   */
  public double getHexagonHeight() {
    return hexagonHeight;
  }

  /**
   * Returns the radius of a hexagon.
   *
   * @return radius of hexagon in meters
   */
  public double getHexagonRadius() {
    return hexagonRadius;
  }

  public double[] getHorizontal() {
    return horizontal;
  }

  public double[] getVertical() {
    return vertical;
  }

  /**
   * Calculates the hexagon surface give a level.
   *
   * Hexagon surface formula:
   * <pre>
   * surface = (3 * sqrt(3) / 2) * radius ^ 2
   * </pre>
   *
   * @param level
   * @return surface
   */
  private double calculateSurface(final int level, final int surfaceLevel1) {
    return surfaceLevel1 * Math.pow(4, level - 1);
  }

  /**
   * Calculates the hexagon radius given a surface.
   *
   * Hexagon radius formula:
   * <pre>
   * radius = sqrt(surface / (3 * sqrt(3) / 2))
   * </pre>
   *
   * @param surface
   * @return radius
   */
  private double calculateRadius(final double surface) {
    final double surfaceRadius = 3.0 * Math.sqrt(3.0) / 2;
    return Math.sqrt(surface / surfaceRadius);
  }

  /**
   * Calculates the hexagon height.
   *
   * The work of art below depicts part of a hexagon (top-top-right)
   * The height of a hexagon is twice the square of the radius to the power of
   * 2 minus half the radius to the power of 2
   * Or: height = 2 * sqrt(pow(radius) - pow(radius / 2)), credits go to
   * pythagoras.
   *
   * Note: As the radius (c) equals a hexagon rib length, half the radius
   * equals half the length of a single hexagon rib (a).
   * <pre>
   *       a (radius * 0.5)
   *     _____
   *    |    /
   *    |   /
   *  b |  /  c (radius)
   *    | /
   *    |/
   *
   * a = radius * 0.5;
   * b = ? (half the hexagon height)
   * c = radius
   *
   * Therefore b = sqrt(b ^ 2 - a ^ 2)
   * </pre>
   */
  private double calculateHexagonHeight(final double radius) {
    return calculateRadiusToHalfHeight(radius) * 2;
  }

  private void fillCorners(final double hexagonRadius, final double hexagonHeight) {
    final double halfHexagonHeight = hexagonHeight / 2;
    for (int i = 0; i < HEXAGON_CORNERS; i++) {
      horizontal[i] =  HORIZONTAL_HEXAGON_MODS[i] * hexagonRadius;
      vertical[i] = VERTICAL_HEXAGON_MODS[i] * halfHexagonHeight;
    }
  }
  /**
   * <pre>
   *       a (radius * 0.5)
   *              _____
   *             |    /
   *             |   /
   *  (height) b |  /  c (radius)
   *             | /
   *             |/
   *
   * a = radius * 0.5;
   * b = x (half the hexagon height)
   * c = radius
   *
   * Therefore b = sqrt(b ^ 2 - a ^ 2)
   * </pre>
   *
   * Model.
   *
   * @param radius of the hexagon
   *
   * @return x
   */
  public static double calculateRadiusToHalfHeight(final double radius) {
    final double half = radius / 2;
    return Math.sqrt((radius * radius) - (half * half));
  }

  @Override
  public String toString() {
    return "ZoomLevel [level=" + level + ", hexagonSurface=" + hexagonSurface + ", hexagonRadius=" + hexagonRadius + ", hexagonHeight="
        + hexagonHeight + "]";
  }
}