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
package nl.aerius.geo.command;

import nl.aerius.wui.command.SimpleCommand;
import nl.aerius.geo.event.MapCenterChangeEvent;

/**
 * Command to center the map on the given coordinate with an optional zoom level to zoom to.
 * Zoom level will be 0 if no zoom level specified.
 */
public class MapCenterChangeCommand extends SimpleCommand<MapCenterChangeEvent> {

  private final double x;
  private final double y;
  private final int zoomLevel;

  public MapCenterChangeCommand(final double x, final double y) {
    this(x, y, 0);
  }

  public MapCenterChangeCommand(final double x, final double y, final int zoomLevel) {
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

  @Override
  protected MapCenterChangeEvent createEvent() {
    return new MapCenterChangeEvent(x, y, zoomLevel);
  }
}
