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
package nl.aerius.geo.command;

import nl.aerius.geo.domain.ReceptorPoint;
import nl.aerius.geo.event.InfoLocationChangeEvent;
import nl.aerius.geo.event.Unscoped;
import nl.aerius.wui.command.SimpleGenericCommand;

public class InfoLocationChangeCommand extends SimpleGenericCommand<ReceptorPoint, InfoLocationChangeEvent> implements Unscoped {
  public InfoLocationChangeCommand(final ReceptorPoint obj) {
    super(obj);
  }

  @Override
  protected InfoLocationChangeEvent createEvent(final ReceptorPoint value) {
    return new InfoLocationChangeEvent(value);
  }
}
