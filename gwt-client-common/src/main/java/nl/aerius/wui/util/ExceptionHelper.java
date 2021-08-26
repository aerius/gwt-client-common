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

package nl.aerius.wui.util;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.UmbrellaException;

import nl.aerius.wui.dev.GWTProd;
import nl.aerius.wui.init.ExceptionalPirateCache;

/**
 * Utility class with some helper related to exception handling.
 */
public final class ExceptionHelper {

  private ExceptionHelper() {
    // Util class
  }

  /**
   * Sets the global handler to uncaught exceptions.
   *
   * @param eventBus     event bus
   * @param isProduction true is this is the production configuration
   */
  public static void setUncaughtExceptionHandler(final EventBus eventBus, final boolean isProduction) {
    GWTProd.setIsDev(!isProduction);
    GWT.setUncaughtExceptionHandler(e -> reportUltimateUncaughtException(eventBus, e));
  }

  public static void reportUltimateUncaughtException(final EventBus eventBus, final Throwable e) {
    final Throwable cause = findCause(e);
    if (cause == null) {
      // If the cause is null for whatever reason, report the parent
      reportUncaughtException(eventBus, e);
    } else {
      reportUncaughtException(eventBus, cause);
    }
  }

  public static void reportUncaughtException(final EventBus eventBus, final Throwable cause) {
    // Print the stacktrace
    cause.printStackTrace(); // This does not work sometimes.
    GWTProd.error("Uncaught exception", cause);

    // Also log to GWT.log because it has special behaviour for umbrella exceptions
    // and stack trace deobfuscation
    GWT.log("", cause);

    // Finally, broadcast an error to notify the user
    NotificationUtil.broadcastError(eventBus, cause);
  }

  public static Throwable findCause(final Throwable e) {
    final Throwable piratedException = ExceptionalPirateCache.pop();
    if (piratedException != null) {
      GWTProd.warn("Exception obfuscated. Fetching from cache.");
      return findCause(piratedException);
    }

    if (e == null) {
      return null;
    }

    if (e instanceof UmbrellaException) {
      return unpack((UmbrellaException) e);
    }

    if (e.getCause() != null) {
      return findCause(e.getCause());
    } else {
      return e;
    }
  }

  private static Throwable unpack(final UmbrellaException e) {
    // Return the last cause (i.e. the actual cause / deepest cause)
    // Note, when GWT logs the umbrella exception, it will log the first cause (i.e.
    // not the actual cause but some handler), and it will suppress logging of all
    // further causes, meaning you'd have to click through to the deeper cause that
    // we are actually interested in
    return e.getCauses().stream()
        .skip(e.getCauses().size() - 1)
        .findFirst().orElse(null);
  }
}
