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

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Data class for legend with a list of legend names with for each name
 * a color.
 */
public class ColorLabelsLegend implements Legend {
  private static final long serialVersionUID = 1L;

  private static final String DEFAULT_OUTLINE_COLOR = "white";

  private String unit;
  private String[] labels;
  private String[] colors;
  private String outlineColor;
  private Integer[] iconSizes;
  private LegendType icon;
  private String[] filterables;

  // Needed for GWT.
  public ColorLabelsLegend() {
  }

  public ColorLabelsLegend(final String[] labels, final String[] colors, final LegendType icon, final String unit) {
    this(labels, colors, icon, new Integer[labels.length], unit, DEFAULT_OUTLINE_COLOR);
  }

  public ColorLabelsLegend(final String[] labels, final String[] colors, final LegendType icon) {
    this(labels, colors, icon, new Integer[labels.length], null, DEFAULT_OUTLINE_COLOR);
  }

  public ColorLabelsLegend(final String[] labels, final String[] colors, final String outlineColor, final LegendType icon) {
    this(labels, colors, icon, new Integer[labels.length], null, outlineColor);
  }

  public ColorLabelsLegend(final String[] labels, final String[] colors, final LegendType icon, final Integer[] iconSizes, final String unit) {
    this(labels, colors, icon, iconSizes, unit, DEFAULT_OUTLINE_COLOR);
  }

  public ColorLabelsLegend(final String[] labels, final String[] colors, final LegendType icon, final Integer[] iconSizes, final String unit,
      final String outlineColor) {

    this.unit = unit;
    this.icon = icon;
    assert labels.length == colors.length : "Legend names list different size as colors list for " + this;
    assert icon.hasColorLegend() : "Unsupported LegendType for ColorLabelsLegend: " + icon;
    this.labels = labels;
    this.colors = colors;
    this.iconSizes = iconSizes;
    this.outlineColor = outlineColor;
  }

  public String[] getColors() {
    return colors;
  }

  public String[] getLabels() {
    return labels;
  }

  public LegendType getIcon() {
    return icon;
  }

  public String getUnit() {
    return unit;
  }

  public Integer[] getIconSizes() {
    return iconSizes;
  }

  public void setColors(final String[] colors) {
    this.colors = colors;
  }

  public void setLabels(final String[] labels) {
    this.labels = labels;
  }

  public void setIcon(final LegendType icon) {
    this.icon = icon;
  }

  public void setUnit(final String unit) {
    this.unit = unit;
  }

  public void setIconSizes(final Integer[] iconSizes) {
    this.iconSizes = iconSizes;
  }

  public String getOutlineColor() {
    return outlineColor;
  }

  public void setOutlineColor(final String outlineColor) {
    this.outlineColor = outlineColor;
  }

  public String[] getFilterables() {
    return filterables;
  }

  public void setFilterables(final String[] filterables) {
    this.filterables = filterables;
  }

  /**
   * Get the amount of legend items.
   *
   * @return size
   */
  public int size() {
    return labels.length;
  }

  /**
   * Adds a single item to the legend
   * @param label string label
   * @param color string color
   * @param iconSize optional iconSize
   */
  public void addItem(final String label, final String color, final Integer iconSize) {
    this.labels = Stream.concat(Arrays.stream(this.labels), Stream.of(label)).toArray(String[]::new);
    this.colors = Stream.concat(Arrays.stream(this.colors), Stream.of(color)).toArray(String[]::new);
    this.iconSizes = Stream.concat(Arrays.stream(this.iconSizes), Stream.of(iconSize)).toArray(Integer[]::new);
  }

  /**
   * Adds a single item to the legend
   * @param label string label
   * @param color string color
   * @param iconSize optional iconSize
   * @param filterable Filterable to use for this item.
   */
  public void addItem(final String label, final String color, final Integer iconSize, final String filterable) {
    addItem(label, color, iconSize);
    this.filterables = filterables == null
        ? new String[] {filterable}
        : Stream.concat(Arrays.stream(this.filterables), Stream.of(filterable)).toArray(String[]::new);
  }

  @Override
  public String toString() {
    return "ColorLabelsLegend[" +
        "labels=" + Arrays.toString(labels) +
        ", colors=" + Arrays.toString(colors) +
        ", icon=" + icon +
        ", outlineColor=" + outlineColor +
        ']';
  }
}
