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
package nl.aerius.geo.domain.legend;

import java.io.Serializable;
import java.util.Objects;

public class ColorRange implements Serializable, Comparable<ColorRange> {
  private static final long serialVersionUID = 1L;

  private String color;
  private double lowerValue;
  private String label;

  /**
   * Default constructor.
   */
  public ColorRange() {
    // GWT required constructor.
  }

  /**
   * @param lowerValue The lower value for this range.
   * @param color      The color to use for this range.
   */
  public ColorRange(final double lowerValue, final String color) {
    this.lowerValue = lowerValue;
    this.color = color;
  }

  /**
   * @param lowerValue The lower value for this range.
   * @param color      The color to use for this range.
   * @param label      (Optional) Label to use for this range.
   */
  public ColorRange(final double lowerValue, final String color, final String label) {
    this.lowerValue = lowerValue;
    this.color = color;
    this.label = label;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ColorRange that = (ColorRange) o;
    return Double.compare(that.lowerValue, lowerValue) == 0
        && Objects.equals(color, that.color)
        && Objects.equals(label, that.label);
  }

  @Override
  public int hashCode() {
    return Objects.hash(color, lowerValue, label);
  }

  public String getColor() {
    return color;
  }

  /**
   * @deprecated for parsing purposes only
   */
  @Deprecated
  public void setColor(final String color) {
    this.color = color;
  }

  public double getLowerValue() {
    return lowerValue;
  }

  /**
   * @deprecated for parsing purposes only
   */
  @Deprecated
  public void setLowerValue(final double lowerValue) {
    this.lowerValue = lowerValue;
  }

  public String getLabel() {
    return label;
  }

  /**
   * @deprecated for parsing purposes only
   */
  @Deprecated
  public void setLabel(final String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return "ColorRange{" +
        "color='" + color + '\'' +
        ", lowerValue=" + lowerValue +
        ", label='" + label + '\'' +
        '}';
  }

  @Override
  public int compareTo(final ColorRange range) {
    return Double.compare(getLowerValue(), range.getLowerValue());
  }
}
