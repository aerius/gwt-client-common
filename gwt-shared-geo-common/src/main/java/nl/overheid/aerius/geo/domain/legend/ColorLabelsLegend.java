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
package nl.overheid.aerius.geo.domain.legend;

import java.util.Arrays;

/**
 * Data class for legend with a list of legend names with for each name
 * a color.
 */
public class ColorLabelsLegend implements Legend {
  private static final long serialVersionUID = 1L;

  private String unit;
  private String[] labels;
  private String[] colors;
  private Integer[] iconSizes;
  private LegendType icon;

  // Needed for GWT.
  public ColorLabelsLegend() {}

  public ColorLabelsLegend(final String[] labels, final String[] colors, final LegendType icon, final String unit) {
    this(labels, colors, icon, new Integer[labels.length], unit);
  }

  public ColorLabelsLegend(final String[] labels, final String[] colors, final LegendType icon) {
    this(labels, colors, icon, new Integer[labels.length], null);
  }

  public ColorLabelsLegend(final String[] labels, final String[] colors, final LegendType icon, final Integer[] iconSizes, final String unit) {
    this.unit = unit;
    this.icon = icon;
    assert labels.length == colors.length : "Legend names list different size as colors list for " + this;
    assert icon.hasColorLegend() : "Unsupported LegendType for ColorLabelsLegend: " + icon;
    this.labels = labels;
    this.colors = colors;
    this.iconSizes = iconSizes;
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

  /**
   * Get the amount of legend items.
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
            ']';
  }
}
