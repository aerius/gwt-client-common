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
package nl.aerius.geo.event;

import com.google.web.bindery.event.shared.binder.GenericEvent;

/**
 * Event when map centered on the given coordinate with an optional zoom level.
 * Zoom level will be 0 if no zoom level specified.
 */
public class MapCenterChangeEvent extends GenericEvent {

  private final double x;
  private final double y;
  private final int zoomLevel;

  public MapCenterChangeEvent(final double x, final double y) {
    this(x, y, 0);
  }

  public MapCenterChangeEvent(final double x, final double y, final int zoomLevel) {
    this.x = x;
    this.y = y;
    this.zoomLevel = zoomLevel;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZoomLevel() {
    return zoomLevel;
  }
}
