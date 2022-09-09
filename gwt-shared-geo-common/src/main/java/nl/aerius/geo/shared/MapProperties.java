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

import nl.overheid.aerius.geo.shared.BBox;

/**
 * Data class properties to initialize a OpenLayers map.
 */
public class MapProperties {

  private String epsg;
  private BBox bounds;
  private double centerX;
  private double centerY;
  private int maxZoomLevel;

  /**
   * Construct a new MapProps.
   *
   * @param srid The EPSG SRID, e.g. EPSG:28992
   * @param bounds The bounderies of the map.
   * @param centerX The center X coordinate of the map
   * @param centerY The center Y coordinate of the map
   * @param maxZoomLevel The maximum zoomLevel that can be zoomed in
   */
  public MapProperties(final String epsg, final BBox bounds, final double centerX, final double centerY, final int maxZoomLevel) {
    this.epsg = epsg;
    this.bounds = bounds;
    this.centerX = centerX;
    this.centerY = centerY;
    this.maxZoomLevel = maxZoomLevel;
  }

  public String getEpsg() {
    return epsg;
  }

  public BBox getBounds() {
    return bounds;
  }

  public double getCenterX() {
    return centerX;
  }

  public double getCenterY() {
    return centerY;
  }

  public int getMaxZoomLevel() {
    return maxZoomLevel;
  }
}
