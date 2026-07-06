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

import java.util.List;
import java.util.function.Function;

/**
 * Builder class to create a {@link ColorLabelsLegend} with a range labels.
 */
public class ColorRangesLegendBuilder {

  private static final String LOWER_THAN = "< ";
  private static final String EQUALS_OR_HIGHER_THAN = "≥ ";
  private static final String SPACE = " ";

  private List<ColorRange> colorRanges;
  private Function<Double, String> labelDeducer = v -> v.toString();
  private String rangeText;
  private String postfix = "";
  private LegendType icon;
  private String unit;

  private ColorRangesLegendBuilder() {
  }

  public static ColorRangesLegendBuilder builder() {
    return new ColorRangesLegendBuilder();
  }

  public ColorRangesLegendBuilder colorRanges(final List<ColorRange> colorRanges) {
    this.colorRanges = colorRanges;
    return this;
  }

  /**
   * Function that is used to convert a range value to a label. If not set the toString method will be set.
   *
   * @param labelDeducer Label deducer function
   */
  public ColorRangesLegendBuilder labelDeducer(final Function<Double, String> labelDeducer) {
    this.labelDeducer = labelDeducer;
    return this;
  }

  public ColorRangesLegendBuilder rangeText(final String rangeText) {
    this.rangeText = rangeText;
    return this;
  }

  public ColorRangesLegendBuilder postfix(final String postfix) {
    this.postfix = postfix;
    return this;
  }

  public ColorRangesLegendBuilder icon(final LegendType icon) {
    this.icon = icon;
    return this;
  }

  public ColorRangesLegendBuilder unit(final String unit) {
    this.unit = unit;
    return this;
  }

  public ColorLabelsLegend build() {
    return ColorLabelsLegend.builder()
        .labels(getLabels(colorRanges, labelDeducer, rangeText, postfix))
        .colors(getColors(colorRanges))
        .icon(icon)
        .unit(unit)
        .build();
  }

  static String[] getLabels(final List<ColorRange> colorRanges, final Function<Double, String> labelDeducer,
      final String rangeText, final String postfix) {
    final String[] labels = new String[colorRanges.size()];
    for (int i = 0; i < colorRanges.size(); i++) {
      if (colorRanges.get(i).getLabel() != null) {
        labels[i] = colorRanges.get(i).getLabel();
      } else {
        // Otherwise try to deduce the label from the range
        labels[i] = deduceLabel(colorRanges, labelDeducer, i, rangeText) + postfix;
      }
    }
    return labels;
  }

  private static String deduceLabel(final List<ColorRange> colorRanges, final Function<Double, String> labelDeducer, final int index,
      final String rangeText) {
    final String spacedRangeText = SPACE + rangeText + SPACE;
    if (index != colorRanges.size() - 1) {
      if (Double.isInfinite(-colorRanges.get(index).getLowerValue())) {
        return LOWER_THAN + labelDeducer.apply(colorRanges.get(index + 1).getLowerValue());
      } else {
        return labelDeducer.apply(colorRanges.get(index).getLowerValue()) + spacedRangeText
            + labelDeducer.apply(colorRanges.get(index + 1).getLowerValue());
      }
    } else {
      return EQUALS_OR_HIGHER_THAN + labelDeducer.apply(colorRanges.get(index).getLowerValue());
    }
  }

  static String[] getColors(final List<ColorRange> colorRanges) {
    return colorRanges.stream().map(ColorRange::getColor).toArray(String[]::new);
  }
}
