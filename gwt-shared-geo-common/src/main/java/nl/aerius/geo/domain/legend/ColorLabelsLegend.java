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

  /**
   * Constructor.
   *
   * @deprecated Use the builder class to construct this class.
   */
  @Deprecated
  public ColorLabelsLegend(final String[] labels, final String[] colors, final LegendType icon, final String unit) {
    this(labels, colors, icon, new Integer[labels.length], unit, DEFAULT_OUTLINE_COLOR);
  }

  /**
   * Constructor.
   *
   * @deprecated Use the builder class to construct this class.
   */
  @Deprecated
  public ColorLabelsLegend(final String[] labels, final String[] colors, final LegendType icon) {
    this(labels, colors, icon, new Integer[labels.length], null, DEFAULT_OUTLINE_COLOR);
  }

  /**
   * Constructor.
   *
   * @deprecated Use the builder class to construct this class.
   */
  @Deprecated
  public ColorLabelsLegend(final String[] labels, final String[] colors, final String outlineColor, final LegendType icon) {
    this(labels, colors, icon, new Integer[labels.length], null, outlineColor);
  }

  /**
   * Constructor.
   *
   * @deprecated Use the builder class to construct this class.
   */
  @Deprecated
  public ColorLabelsLegend(final String[] labels, final String[] colors, final LegendType icon, final Integer[] iconSizes, final String unit) {
    this(labels, colors, icon, iconSizes, unit, DEFAULT_OUTLINE_COLOR);
  }

  /**
   * Constructor.
   *
   * @deprecated Use the builder class to construct this class.
   */
  @Deprecated
  public ColorLabelsLegend(final String[] labels, final String[] colors, final LegendType icon, final Integer[] iconSizes, final String unit,
      final String outlineColor) {
    this(labels, colors, icon, iconSizes, unit, outlineColor, null);
  }

  private ColorLabelsLegend(final String[] labels, final String[] colors, final LegendType icon, final Integer[] iconSizes, final String unit,
      final String outlineColor, final String[] filterables) {
    this.unit = unit;
    this.icon = icon;
    assert labels.length == colors.length : "Legend names list different size as colors list for " + this;
    assert icon.hasColorLegend() : "Unsupported LegendType for ColorLabelsLegend: " + icon;
    this.labels = labels;
    this.colors = colors;
    this.iconSizes = iconSizes;
    this.outlineColor = outlineColor;
    this.filterables = filterables;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String[] colors() {
    return colors;
  }

  /**
   * @deprecated Use the {@link #colors()} method to be ready for this class to be converted to a record.
   */
  @Deprecated
  public String[] getColors() {
    return colors;
  }

  public String[] labels() {
    return labels;
  }

  /**
   * @deprecated Use the {@link #labels()} method to be ready for this class to be converted to a record.
   */
  @Deprecated
  public String[] getLabels() {
    return labels;
  }

  public LegendType icon() {
    return icon;
  }

  /**
   * @deprecated Use the {@link #icon()} method to be ready for this class to be converted to a record.
   */
  @Deprecated
  public LegendType getIcon() {
    return icon;
  }

  public String unit() {
    return unit;
  }

  /**
   * @deprecated Use the {@link #unit()} method to be ready for this class to be converted to a record.
   */
  @Deprecated
  public String getUnit() {
    return unit;
  }

  public Integer[] iconSizes() {
    return iconSizes;
  }

  /**
   * @deprecated Use the {@link #iconSizes()} method to be ready for this class to be converted to a record.
   */
  @Deprecated
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

  public String outlineColor() {
    return outlineColor;
  }

  /**
   * @deprecated Use the {@link #outlineColor()} method to be ready for this class to be converted to a record.
   */
  @Deprecated
  public String getOutlineColor() {
    return outlineColor;
  }

  public void setOutlineColor(final String outlineColor) {
    this.outlineColor = outlineColor;
  }

  public String[] filterables() {
    return filterables;
  }

  /**
   * @deprecated Use the {@link #filterables()} method to be ready for this class to be converted to a record.
   */
  @Deprecated
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
        ", iconSizes=" + Arrays.toString(iconSizes) +
        ", unit=" + unit +
        ", outlineColor=" + outlineColor +
        ", filterables=" + Arrays.toString(filterables) +
        ']';
  }

  public static class Builder {
    private String unit;
    private String[] labels;
    private String[] colors;
    private String outlineColor = DEFAULT_OUTLINE_COLOR;
    private Integer[] iconSizes;
    private LegendType icon;
    private String[] filterables = new String[0];

    public Builder unit(final String unit) {
      this.unit = unit;
      return this;
    }

    public Builder labels(final String[] labels) {
      this.labels = labels;
      return this;
    }

    public Builder colors(final String[] colors) {
      this.colors = colors;
      return this;
    }

    public Builder outlineColor(final String outlineColor) {
      this.outlineColor = outlineColor;
      return this;
    }

    public Builder iconSizes(final Integer[] iconSizes) {
      this.iconSizes = iconSizes;
      return this;
    }

    public Builder icon(final LegendType icon) {
      this.icon = icon;
      return this;
    }

    public Builder filterables(final String[] filterables) {
      this.filterables = filterables;
      return this;
    }

    public ColorLabelsLegend build() {
      return new ColorLabelsLegend(labels, colors, icon, iconSizes, unit, outlineColor, filterables);
    }
  }

}
