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
package nl.aerius.wui.event;

import nl.aerius.wui.command.SimpleGenericCommand;

public class FireEventComponent extends BasicEventComponent {
  protected <T> void fireIfChanged(final SimpleGenericEvent<T> event, final T previous, final T current) {
    if (previous == null) {
      if (current == null) {
        return;
      } else {
        fire(event, current);
        return;
      }
    }

    if (!previous.equals(current)) {
      fire(event, current);
      return;
    }
  }

  protected <T extends Object> void fire(final SimpleGenericEvent<T> event, final T current) {
    // If the event to fire is a command - silence it.
    if (event instanceof SimpleGenericCommand) {
      ((SimpleGenericCommand<?, ?>) event).setSilent(true);
    }

    event.setValue(current);
    eventBus.fireEvent(event);
  }
}
