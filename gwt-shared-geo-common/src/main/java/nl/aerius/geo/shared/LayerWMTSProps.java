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
 * Layer properties for WMTS layers.
 */
public class LayerWMTSProps extends LayerProps implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The format extension corresponding to the requested tile image type.
   */
  private String type = "png8";

  /**
   * Service version for tile requests.
   */
  private String serviceVersion = "1.0.0";

  /**
   * Projection of the WMTS tile grid, like EPSG:28992
   */
  private String projection;

  /**
   * WMTS Tile matrix set. This can be equal to the projection, or an identifier supplied by the map provider.
   */
  private String tileMatrixSet;

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getServiceVersion() {
    return serviceVersion;
  }

  public void setServiceVersion(final String serviceVersion) {
    this.serviceVersion = serviceVersion;
  }

  public String getProjection() {
    return projection;
  }

  public void setProjection(String projection) {
    this.projection = projection;
  }

  public String getTileMatrixSet() {
    return tileMatrixSet;
  }

  public void setTileMatrixSet(final String tileMatrixSet) {
    this.tileMatrixSet = tileMatrixSet;
  }
}
