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
package nl.aerius.wui.init;

/**
 * The browser sandboxes uncaught exceptions when they are thrown following a
 * cross-origin request, and replaces them with a javascript exception that has
 * none of the information we need. This static cache hides the exception,
 * allowing the uncaught exception handler to retrieve it.
 *
 * Note this class has a single-purpose utility, hence the reason using static
 * fields here is acceptable, and should not be used in the nominal case.
 */
public class ExceptionalPirateCache {
  private static Throwable last;

  public static void hide(final Throwable last) {
    ExceptionalPirateCache.last = last;
  }

  public static Throwable pop() {
    final Throwable t = last;
    last = null;
    return t;
  }
}
