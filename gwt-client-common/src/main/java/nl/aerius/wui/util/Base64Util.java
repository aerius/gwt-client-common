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

public class Base64Util {
  /**
   * decodes a string of data which has been encoded using base-64 encoding
   */
  public static native String decode(String str) /*-{
    return window.atob(str);
  }-*/;

  /**
   * creates a base-64 encoded ASCII string from a "string" of binary data.
   */
  public static native String encode(String str) /*-{
    return window.btoa(str);
  }-*/;
}
