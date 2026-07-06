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
 *
 * @deprecated Use {@link ColorRangesLegendBuilder} to create this legend.
 */
@Deprecated
public class ColorRangesLegend extends ColorLabelsLegend {
  private static final long serialVersionUID = 1L;

  public ColorRangesLegend() {
  }

  public ColorRangesLegend(final List<ColorRange> colorRanges, final String rangeText, final LegendType icon) {
    super(ColorRangesLegendBuilder.getLabels(colorRanges, v -> v.toString(), rangeText, ""), ColorRangesLegendBuilder.getColors(colorRanges), icon,
        null);
  }

  public ColorRangesLegend(final List<ColorRange> colorRanges, final String rangeText, final LegendType icon, final String unit) {
    super(ColorRangesLegendBuilder.getLabels(colorRanges, v -> v.toString(), rangeText, ""), ColorRangesLegendBuilder.getColors(colorRanges), icon,
        unit);
  }

  public ColorRangesLegend(final List<ColorRange> colorRanges, final Function<Double, String> labelDeducer, final String rangeText,
      final LegendType icon, final String unit) {
    super(ColorRangesLegendBuilder.getLabels(colorRanges, labelDeducer, rangeText, ""), ColorRangesLegendBuilder.getColors(colorRanges), icon, unit);
  }
}
