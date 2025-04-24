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

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColorItem implements Serializable, Comparable<ColorItem> {
  private static final long serialVersionUID = 1L;

  private String color;
  private int lineWidth;
  private String itemValue;
  private @JsonProperty int sortOrder;

  /**
   * Default constructor.
   */
  public ColorItem() {
    // GWT required constructor.
  }

  /**
   * @param itemValue The value for this item.
   * @param color     The color to use for this item.
   * @param sortOrder The sorting order to use.
   */
  public ColorItem(final String itemValue, final String color, final int lineWidth, final int sortOrder) {
    this.itemValue = itemValue;
    this.color = color;
    this.lineWidth = lineWidth;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ColorItem that = (ColorItem) o;
    return Objects.equals(itemValue, that.itemValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemValue);
  }

  public String getColor() {
    return color;
  }

  public int getLineWidth() {
    return lineWidth;
  }

  public String getItemValue() {
    return itemValue;
  }

  @Override
  public String toString() {
    return "ColorRange{" +
        "color='" + color + '\'' +
        "lineWidth=" + lineWidth +
        ", itemValue=" + itemValue +
        '}';
  }

  @Override
  public int compareTo(final ColorItem item) {
    return Integer.compare(sortOrder, item.sortOrder);
  }

  /**
   * @deprecated for parsing purposes only
   */
  @Deprecated
  public void setColor(final String color) {
    this.color = color;
  }

  /**
   * @deprecated for parsing purposes only
   */
  @Deprecated
  public void setLineWidth(final int lineWidth) {
    this.lineWidth = lineWidth;
  }

  /**
   * @deprecated for parsing purposes only
   */
  @Deprecated
  public void setItemValue(final String itemValue) {
    this.itemValue = itemValue;
  }

  /**
   * @deprecated for parsing purposes only
   */
  @Deprecated
  public void setSortOrder(final int sortOrder) {
    this.sortOrder = sortOrder;
  }
}
