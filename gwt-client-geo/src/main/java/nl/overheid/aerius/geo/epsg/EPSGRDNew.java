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

import com.google.inject.Singleton;

import nl.overheid.aerius.geo.BBox;

/**
 * Constants for RD new coordinates.
 */
@Singleton
public final class EPSGRDNew extends EPSG {
  public static final int SRID = 28992;

  private static final double[] RESOLUTIONS =
    { 3440.64, 1720.32, 860.16, 430.08, 215.04, 107.52, 53.76, 26.88, 13.44 };
  private static final float MAX_RESOLUTION = 0;
  private static final int ZOOM_LEVEL = 14;
  private static final String UNIT = "km";
  private static final BBox BOUNDS = new BBox(-285401.920, 22598.080, 595401.920, 903401.920);

  public EPSGRDNew() {
    super(SRID, BOUNDS, RESOLUTIONS, MAX_RESOLUTION, UNIT, ZOOM_LEVEL);
  }
}
