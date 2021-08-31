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

import elemental2.dom.DomGlobal;

/**
 * Apparently varargs don't work swimmingly, so implement a bunch of overloads instead.
 */
public final class GWTProd {
  private static boolean dev = true;

  private GWTProd() {}

  public static void setIsDev(final boolean dev) {
    GWTProd.dev = dev;
  }

  public static boolean isDev() {
    return dev;
  }

  public static void log(final Object msg) {
    DomGlobal.console.log(msg);
  }

  public static void log(final Object a, final Object b) {
    DomGlobal.console.log(a, b);
  }

  public static void log(final Object a, final Object b, final Object c) {
    DomGlobal.console.log(a, b, c);
  }

  public static void info(final Object msg) {
    DomGlobal.console.info(msg);
  }

  public static void info(final Object a, final Object b) {
    DomGlobal.console.info(a, b);
  }

  public static void info(final Object a, final Object b, final Object c) {
    DomGlobal.console.info(a, b, c);
  }

  public static void warn(final Object msg) {
    DomGlobal.console.warn(msg);
  }

  public static void warn(final Object a, final Object b) {
    DomGlobal.console.warn(a, b);
  }

  public static void warn(final Object a, final Object b, final Object c) {
    DomGlobal.console.warn(a, b, c);
  }

  public static void error(final Object msg) {
    DomGlobal.console.warn(msg);
    tryReport(msg);
  }

  public static void error(final Object a, final Object b) {
    DomGlobal.console.error(a, b);
    tryReport(a);
    tryReport(b);
  }

  public static void error(final Object a, final Object b, final Object c) {
    DomGlobal.console.error(a, b, c);
    tryReport(a);
    tryReport(b);
    tryReport(c);
  }

  private static void tryReport(final Object ex) {
    if (ex instanceof Throwable) {
      ((Throwable) ex).printStackTrace();
    }
  }
}
