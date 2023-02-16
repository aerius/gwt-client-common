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
package nl.aerius.wui.dev;

import java.util.stream.Stream;

import elemental2.dom.DomGlobal;

/**
 * Conditional log in the browser console.
 */
public final class GWTProd {
  private static boolean debug = false;
  private static boolean dev = true;

  private GWTProd() {}

  public static void setDebug(final boolean debug) {
    GWTProd.debug = debug;
  }

  public static void setIsDev(final boolean dev) {
    GWTProd.dev = dev;
  }

  public static boolean isDebug() {
    return debug;
  }

  public static boolean isDev() {
    return dev;
  }

  public static void log(final Object... msg) {
    logIfDebug(() -> DomGlobal.console.log(msg));
  }

  public static void info(final Object... msg) {
    logIfDebug(() -> DomGlobal.console.info(msg));
  }

  public static void warn(final Object... msg) {
    logIfDebug(() -> DomGlobal.console.warn(msg));
  }

  public static void error(final Object... msg) {
    logIfDebug(() -> {
      DomGlobal.console.error(msg);
      tryReport(msg);
    });
  }

  private static void tryReport(final Object... ex) {
    Stream.of(ex).filter(e -> e instanceof Throwable).forEach(e -> ((Throwable) e).printStackTrace());
  }

  private static void logIfDebug(final Runnable log) {
    if (debug) {
      log.run();
    }
  }
}
