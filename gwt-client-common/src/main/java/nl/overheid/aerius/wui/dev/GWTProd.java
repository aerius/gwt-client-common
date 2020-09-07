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
package nl.overheid.aerius.wui.dev;

public class GWTProd {

  /**
   * Logs a message to the console. Calls are _not_ optimized out in Production Mode.
   */
  public static native void log(String marker, Object message) /*-{
    console.info(marker, message);
  }-*/;
  
  /**
   * Logs a message to the console. Calls are _not_ optimized out in Production Mode.
   */
  public static native void log(Object message) /*-{
    console.info(message );
  }-*/;

  /**
   * Logs a message to the warn console. Calls are _not_ optimized out in Production Mode.
   */
  public static native void warn(Object message) /*-{
    console.warn(message );
  }-*/; 

  /**
   * Logs a message to the warn console. Calls are _not_ optimized out in Production Mode.
   */
  public static native void error(Object message) /*-{
    console.error(message);
  }-*/;

  public static void info(final String string) {
    log("INFO", string);
  }

  public static void error(final String marker, final String string) {
    error("[" + marker + "] " + string); 
  }

  public static void error(final String marker, final String string, final Exception e) {
    error("[" + marker + "] " + string, e);
  }

  public static void error(final String string, final Throwable e) {
    error(string); 
    e.printStackTrace();
  }

  public static void warn(final String marker, final String string) {
    warn("[" + marker + "] " + string);
  }
  
  public static native void time(String marker) /*-{
    console.time(marker);
  }-*/;
  
  public static native void timeEnd(String marker) /*-{
    console.timeEnd(marker);
  }-*/;
}
