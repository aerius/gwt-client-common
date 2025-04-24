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
 * Legend that is extracted from a color range
 */
public class ColorRangesLegend extends ColorLabelsLegend {
  private static final long serialVersionUID = 1L;
  private static final String LOWER_THAN = "< ";
  private static final String EQUALS_OR_HIGHER_THAN = "≥ ";
  private static final String SPACE = " ";

  public ColorRangesLegend() {
  }

  public ColorRangesLegend(final List<ColorRange> colorRanges, final String rangeText, final LegendType icon) {
    super(getLabels(colorRanges, v -> v.toString(), rangeText), getColors(colorRanges), icon, null);
  }

  public ColorRangesLegend(final List<ColorRange> colorRanges, final String rangeText, final LegendType icon,
      final String unit) {
    super(getLabels(colorRanges, v -> v.toString(), rangeText), getColors(colorRanges), icon, unit);
  }

  public ColorRangesLegend(final List<ColorRange> colorRanges, final Function<Double, String> labelDeducer,
      final String rangeText,
      final LegendType icon, final String unit) {
    super(getLabels(colorRanges, labelDeducer, rangeText), getColors(colorRanges), icon, unit);
  }

  private static String[] getLabels(final List<ColorRange> colorRanges, final Function<Double, String> labelDeducer,
      final String rangeText) {
    final String[] labels = new String[colorRanges.size()];
    for (int i = 0; i < colorRanges.size(); i++) {
      if (colorRanges.get(i).getLabel() != null) {
        labels[i] = colorRanges.get(i).getLabel();
      } else {
        // Otherwise try to deduce the label from the range
        labels[i] = deduceLabel(colorRanges, labelDeducer, i, rangeText);
      }
    }
    return labels;
  }

  private static String deduceLabel(final List<ColorRange> colorRanges, final Function<Double, String> labelDeducer,
      final int index, final String rangeText) {
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

  private static String[] getColors(final List<ColorRange> colorRanges) {
    return colorRanges.stream().map(ColorRange::getColor).toArray(String[]::new);
  }

}
