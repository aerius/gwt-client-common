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
 * Data class for legend with a list of makers icons and names.
 */
public class MarkerLabelsLegend implements Legend {
  private static final long serialVersionUID = 1L;

  private String[] markers;

  // Needed for GWT.
  public MarkerLabelsLegend() {}

  public MarkerLabelsLegend(final String[] markers) {
    this.markers = markers;
  }

  public String[] getMarkers() {
    return markers;
  }

  public void setMarkers(final String[] markers) {
    this.markers = markers;
  }

  @Override
  public String toString() {
    return "MarkerLabelsLegend[" +
        ", markers=" + Arrays.toString(markers) +
        ']';
  }
}
