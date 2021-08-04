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
package nl.aerius.wui.command;

import com.google.web.bindery.event.shared.Event;

/**
 * A {@link Command} is a call to change the state of something.
 * This can be a global context variable or a ui state.
 * If the command is handled a Event will be fired to report the state was changed.
 * Those who are responsible for the state change need to listen for commands.
 * Those who need to react to this state change need to listen to the events.
 *
 * The event following the command can be silenced by setting the silence value to true.
 *
 * @param <H> The corresponding event class.
 */
public interface Command<H extends Event<?>> {

  /**
   * Sets if the event for this should be silenced or not.
   *
   * @param silent if true the event following this command will not be fired.
   */
  void setSilent(boolean silent);

  /**
   * Short-hand for setSilent(true), can be used to silence this command's event.
   *
   * Silencing is useful for example when the command has no effect, or when the functionality is duplicated elsewhere.
   */
  default void silence() {
    setSilent(true);
  }

  /**
   * @return Returns true if no event should be fired following this command.
   */
  boolean isSilent();

  /**
   * @return Get a corresponding event instance for this command.
   */
  H getEvent();
}