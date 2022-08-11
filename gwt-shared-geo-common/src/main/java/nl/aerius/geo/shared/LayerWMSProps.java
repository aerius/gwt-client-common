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
import java.util.HashMap;

/**
 * Layer properties for WMS layers.
 */
public class LayerWMSProps extends LayerProps implements Serializable {
  private static final long serialVersionUID = 4407417224031235525L;

  /**
   * Viewparam filter. Basic version of simpleFilter; limited to params explicitly configured in the geoserver.
   *
   * Params provided will be encoded as <pre>[key]:[value]</pre> and joined with <pre>;</pre>
   */
  private HashMap<String, Object> paramFilters;

  /**
   * If the wms is tiled this sets the size of the tile. If the value is 0 no tiling is used.
   */
  private int tileSize;

  public HashMap<String, Object> getParamFilters() {
    return paramFilters;
  }

  public void setParamFilters(final HashMap<String, Object> paramFilters) {
    this.paramFilters = paramFilters;
  }

  public void setParamFilter(final String key, final Object value) {
    if (paramFilters == null) {
      paramFilters = new HashMap<String, Object>();
    }

    paramFilters.put(key, value);
  }

  public int getTileSize() {
    return tileSize;
  }

  public void setTileSize(final int tileSize) {
    this.tileSize = tileSize;
  }

  /**
   * Copies only part of the properties.
   * @return
   */
  public LayerWMSProps shallowCopy() {
    final LayerWMSProps newLayer = new LayerWMSProps();
    newLayer.setName(this.getName());
    newLayer.setTitle(this.getTitle());
    return newLayer;
  }

  @Override
  public String toString() {
    return "LayerWMSProps [tileSize=" + tileSize + "]" + super.toString();
  }
}
