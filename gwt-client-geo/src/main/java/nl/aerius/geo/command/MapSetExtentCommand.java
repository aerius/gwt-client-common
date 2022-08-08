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

import nl.aerius.wui.command.SimpleGenericCommand;
import nl.aerius.geo.event.MapSetExtentEvent;

public class MapSetExtentCommand extends SimpleGenericCommand<String, MapSetExtentEvent> {
  private boolean userInitiated;
  private boolean fromMap;
  private boolean animated = true;

  public MapSetExtentCommand(final String extent) {
    super(extent);
  }

  public MapSetExtentCommand(final String extent, final boolean userInitiated) {
    super(extent);
    this.userInitiated = userInitiated;
  }

  public MapSetExtentCommand(final String extent, final boolean userInitiated, final boolean fromMap) {
    super(extent);
    this.userInitiated = userInitiated;
    this.fromMap = fromMap;
  }

  public MapSetExtentCommand(final String extent, final boolean userInitiated, final boolean fromMap, final boolean animated) {
    super(extent);
    this.userInitiated = userInitiated;
    this.fromMap = fromMap;
    this.animated = animated;
  }

  @Override
  protected MapSetExtentEvent createEvent(final String value) {
    return new MapSetExtentEvent(value);
  }

  public boolean isUserInitiated() {
    return userInitiated;
  }

  public void setUserInitiated(final boolean userInitiated) {
    this.userInitiated = userInitiated;
  }

  public boolean isFromMap() {
    return fromMap;
  }

  public void setFromMap(final boolean fromMap) {
    this.fromMap = fromMap;
  }

  public boolean isAnimated() {
    return animated;
  }

  public void setAnimated(final boolean animated) {
    this.animated = animated;
  }
}
