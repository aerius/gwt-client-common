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

package nl.aerius.geo.shared;

import java.io.Serializable;

/**
 * Layer properties for a Bing map.
 */
public class LayerBingProps extends LayerProps implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * The API key to use for map requests
   */
  private String key;
  /**
   * The imagery set to use, one of ['RoadOnDemand', 'Aerial', 'AerialWithLabelsOnDemand', 'CanvasDark', 'OrdnanceSurvey']
   */
  private String imagerySet;

  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public void setImagerySet(final String imagerySet) {
    this.imagerySet = imagerySet;
  }

  public String getImagerySet() {
    return imagerySet;
  }
}
