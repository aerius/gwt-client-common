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
public record ColorLabelsLegend(String[] labels, String[] colors, LegendType icon, Integer[] iconSizes, String unit,
    String outlineColor, String[] filterables) implements Legend {

  public ColorLabelsLegend {
    assert labels.length == colors.length : "Legend names list different size as colors list for " + this;
    assert icon.hasColorLegend() : "Unsupported LegendType for ColorLabelsLegend: " + icon;
  }

  public static Builder builder() {
    return new Builder();
  }

  /**
   * Get the amount of legend items.
   *
   * @return size
   */
  public int size() {
    return labels.length;
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
    private static final String DEFAULT_OUTLINE_COLOR = "white";

    private String unit;
    private String[] labels;
    private String[] colors;
    private String outlineColor = DEFAULT_OUTLINE_COLOR;
    private Integer[] iconSizes;
    private LegendType icon;
    private String[] filterables;

    public Builder unit(final String unit) {
      this.unit = unit;
      return this;
    }

    public Builder labels(final String[] labels) {
      this.labels = labels;
      if (iconSizes == null) {
        iconSizes = new Integer[labels.length];
      }
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

    /**
     * Adds a single item to the legend.
     *
     * @param label string label
     * @param color string color
     * @param iconSize optional iconSize
     */
    public void addItem(final String label, final String color, final Integer iconSize) {
      labels = (labels == null ? Stream.of(label) : Stream.concat(Arrays.stream(labels), Stream.of(label))).toArray(String[]::new);
      colors = (colors == null ? Stream.of(color) : Stream.concat(Arrays.stream(colors), Stream.of(color))).toArray(String[]::new);
      if (iconSize != null) {
        iconSizes = (iconSizes == null ? Stream.of(iconSize) : Stream.concat(Arrays.stream(this.iconSizes), Stream.of(iconSize)))
            .toArray(Integer[]::new);
      }
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

    public ColorLabelsLegend build() {
      return new ColorLabelsLegend(labels, colors, icon, iconSizes, unit, outlineColor, filterables);
    }
  }

}
