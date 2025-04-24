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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * Data class for properties of a layer legend with only a name.
 */
@JsonTypeInfo(property = Legend.LEGEND_TYPE_PROPERTY, use = Id.NAME)
@JsonSubTypes({
    @Type(value = MarkerLabelsLegend.class, name = Legend.TYPE_MARKER_LABELS),
    @Type(value = TextLegend.class, name = Legend.TYPE_TEXT),
    @Type(value = ColorLabelsLegend.class, name = Legend.TYPE_COLOR_LABELS),
    @Type(value = ColorRangesLegend.class, name = Legend.TYPE_COLOR_RANGES),
})
public interface Legend extends Serializable {
  public static final String LEGEND_TYPE_PROPERTY = "legendType";
  public static final String TYPE_MARKER_LABELS = "MARKER_LABELS";
  public static final String TYPE_TEXT = "TEXT";
  public static final String TYPE_COLOR_LABELS = "COLOR_LABELS";
  public static final String TYPE_COLOR_RANGES = "COLOR_RANGES";
}
