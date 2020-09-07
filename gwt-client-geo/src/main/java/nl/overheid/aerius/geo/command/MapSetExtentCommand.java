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
package nl.overheid.aerius.geo.command;

import nl.overheid.aerius.geo.event.MapSetExtentEvent;
import nl.overheid.aerius.wui.command.SimpleGenericCommand;

public class MapSetExtentCommand extends SimpleGenericCommand<String, MapSetExtentEvent> {
  private boolean userInitiated;
  private boolean fromMap;
  private boolean persistOnly;

  public MapSetExtentCommand(final String extent) {
    this(extent, false, false, false);
  }

  public MapSetExtentCommand(final String extent, final boolean userInitiated) {
    this(extent, userInitiated, false, false);
  }

  public MapSetExtentCommand(final String extent, final boolean userInitiated, final boolean fromMap) {
    this(extent, userInitiated, fromMap, false);
  }

  public MapSetExtentCommand(final String extent, final boolean userInitiated, final boolean fromMap, final boolean persistOnly) {
    super(extent);
    this.userInitiated = userInitiated;
    this.fromMap = fromMap;
    this.persistOnly = persistOnly;
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

  public boolean isPersistOnly() {
    return persistOnly;
  }

  public void setPersistOnly(final boolean persistOnly) {
    this.persistOnly = persistOnly;
  }
}
