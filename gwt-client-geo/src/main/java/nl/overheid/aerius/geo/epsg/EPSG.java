/*
 * Copyright Dutch Ministry of Agriculture, Nature and Food Quality
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
package nl.overheid.aerius.geo.epsg;

import nl.overheid.aerius.geo.BBox;

/**
 * EPSG map constants.
 */
public abstract class EPSG {
  private static final String EPSG_PRE_TEXT = "EPSG:";

  private final int srid;
  private final BBox bounds;
  private final float maxResolution;
  private final String unit;
  private final int zoomLevel;

  /**
   * Construct a EPSG object.
   *
   * @param srid The SRID to set.
   * @param bounds The bounds to set.
   * @param resolutions The resolutions to set.
   * @param maxResolution The max resolution to set.
   * @param unit The unit to set.
   * @param zoomLevel The zoomLevel to set.
   */
  public EPSG(final int srid, final BBox bounds, final double[] resolutions, final float maxResolution, final String unit, final int zoomLevel) {
    this.srid = srid;
    this.bounds = bounds;
    this.maxResolution = maxResolution;
    this.unit = unit;
    this.zoomLevel = zoomLevel;
  }

  public String getEpsgCode() {
    return EPSG_PRE_TEXT + getSrid();
  }

  public int getSrid() {
    return srid;
  }

  public BBox getBounds() {
    return bounds;
  }

  public float getMaxResolution() {
    return maxResolution;
  }

  public String getUnit() {
    return unit;
  }

  public int getZoomLevel() {
    return zoomLevel;
  }

  @Override
  public String toString() {
    return "EPSG [srid=" + srid + ", bounds=" + bounds + ", maxResolution=" + maxResolution + ", unit=" + unit + ", zoomLevel=" + zoomLevel + "]";
  }
}
