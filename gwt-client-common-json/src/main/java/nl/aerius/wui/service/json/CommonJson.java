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
package nl.aerius.wui.service.json;

import com.google.gwt.json.client.JSONObject;

public class CommonJson {
  public static String getString(final JSONObject obj, final String key) {
    return new JSONObjectHandle(obj).getString(key);
  }

  public static JSONObjectHandle getObject(final JSONObject obj, final String key) {
    return new JSONObjectHandle(obj).getObject(key);
  }

  public static JSONArrayHandle getArray(final JSONObject obj, final String key) {
    return new JSONObjectHandle(obj).getArray(key);
  }

  public static String getStringOrDefault(final JSONObject obj, final String key, final String devault) {
    try {
      return new JSONObjectHandle(obj).getString(key);
    } catch (final IllegalStateException e) {
      return devault;
    }
  }
}
