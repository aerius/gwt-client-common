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
package nl.overheid.aerius.geo;

import java.io.Serializable;

/**
 * Represents a Bounding Box.
 */
public class BBox implements Serializable {
  private static final long serialVersionUID = -8632974689754535845L;

  private double minX;
  private double maxX;
  private double minY;
  private double maxY;

  /**
   * Creates an empty bounding box.
   */
  public BBox() {}

  /**
   * Creates a BBox in the shape of a square.
   *
   * @param centerX The center X
   * @param centerY The center Y
   * @param ribLength The rib length
   */
  public BBox(final double centerX, final double centerY, final double ribLength) {
    final double correction = ribLength / 2;

    this.minX = centerX - correction;
    this.maxX = centerX + correction;
    this.minY = centerY - correction;
    this.maxY = centerY + correction;
  }

  /**
   * Creates a BBox with the given coordinates.
   *
   * @param minX The minX
   * @param minY The minY
   * @param maxX The maxX
   * @param maxY The maxY
   */
  public BBox(final double minX, final double minY, final double maxX, final double maxY) {
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
  }

  /**
   * Returns a copy of this object.
   *
   * @return the copy
   */
  public BBox copy() {
    return new BBox(minX, minY, maxX, maxY);
  }

  public double getWidth() {
    return getMaxX() - getMinX();
  }

  public double getHeight() {
    return getMaxY() - getMinY();
  }

  public double getMinX() {
    return minX;
  }

  public double getMaxX() {
    return maxX;
  }

  public double getMinY() {
    return minY;
  }

  public double getMaxY() {
    return maxY;
  }

  public double getMidX() {
    return (maxX + minX) / 2;
  }

  public double getMidY() {
    return (maxY + minY) / 2;
  }

  public void setMinX(final double minX) {
    this.minX = minX;
  }

  public void setMaxX(final double maxX) {
    this.maxX = maxX;
  }

  public void setMinY(final double minY) {
    this.minY = minY;
  }

  public void setMaxY(final double maxY) {
    this.maxY = maxY;
  }

  @Override
  public String toString() {
    return "BBox [minX=" + minX + ", maxX=" + maxX + ", minY=" + minY + ", maxY=" + maxY + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    final int bitShift = 32;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(maxX);
    result = prime * result + (int) (temp ^ (temp >>> bitShift));
    temp = Double.doubleToLongBits(maxY);
    result = prime * result + (int) (temp ^ (temp >>> bitShift));
    temp = Double.doubleToLongBits(minX);
    result = prime * result + (int) (temp ^ (temp >>> bitShift));
    temp = Double.doubleToLongBits(minY);
    result = prime * result + (int) (temp ^ (temp >>> bitShift));
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    boolean equal = false;
    if (obj != null && this.getClass() == obj.getClass()) {
      final BBox other = (BBox) obj;
      equal = minX == other.minX && minY == other.minY && maxX == other.maxX && maxY == other.maxY;
    }
    return equal;
  }
}
