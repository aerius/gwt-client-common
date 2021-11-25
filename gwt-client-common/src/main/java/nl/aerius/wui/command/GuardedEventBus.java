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

import nl.aerius.wui.event.CommandEventBus;
import nl.aerius.wui.util.ExceptionHelper;

/**
 * A CommandEventBus that does its own error handling.
 */
public class GuardedEventBus extends CommandEventBus {
  @Override
  protected void tryInnerFire(final boolean enforce, final Event<?> event) {
    try {
      super.tryInnerFire(enforce, event);
    } catch (final Exception e) {
      ExceptionHelper.reportUltimateUncaughtException(this, e);
    }
  }

  @Override
  protected void tryInnerFireFromSource(final boolean enforce, final Event<?> event, final Object source) {
    try {
      super.tryInnerFireFromSource(enforce, event, source);
    } catch (final Exception e) {
      ExceptionHelper.reportUltimateUncaughtException(this, e);
    }
  }
}
