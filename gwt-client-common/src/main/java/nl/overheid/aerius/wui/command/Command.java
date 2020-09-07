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
package nl.overheid.aerius.wui.command;

import com.google.web.bindery.event.shared.Event;

public interface Command<H extends Event<?>> {
  void setSilent(boolean silent);

  /**
   * Short-hand for setSilent(true), can be used to silence this command's event.
   *
   * Silencing is useful for example when the command has no effect, or when the functionality is duplicated elsewhere.
   */
  default void silence() {
    setSilent(true);
  }

  boolean isSilent();

  H getEvent();
}
