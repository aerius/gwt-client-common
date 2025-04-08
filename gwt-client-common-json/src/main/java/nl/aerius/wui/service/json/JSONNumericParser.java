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

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * Helper for parsing JSONValues that might represent numbers,
 * including special string cases ("Infinity", "-Infinity", "NaN").
 */
class JsonNumericParser {

  /**
   * Checks if the value is a JSONNumber or a special FP string ("Infinity",
   * "-Infinity", "NaN").
   * 
   * @param value The JSONValue to check (can be null).
   */
  static boolean isRepresentingNumber(final JSONValue value) {
    if (value == null) {
      return false;
    }
    if (value.isNumber() != null) {
      return true;
    }
    final JSONString jsonString = value.isString();
    if (jsonString != null) {
      try {
        final double parsedValue = Double.parseDouble(jsonString.stringValue());
        return Double.isInfinite(parsedValue) || Double.isNaN(parsedValue);
      } catch (final NumberFormatException e) {
        return false;
      }
    }
    return false;
  }

  /**
   * Parses the JSONValue as a Double, handling JSONNumber and JSONString.
   * Supports special strings "Infinity", "-Infinity", "NaN".
   *
   * @param value The JSONValue to parse.
   * @throws IllegalArgumentException if parsing is not possible.
   */
  static Double parseAsNumber(final JSONValue value) throws IllegalArgumentException {
    if (value == null) {
      throw new IllegalArgumentException("Cannot parse null JSONValue as number");
    }
    final JSONNumber num = value.isNumber();
    if (num != null) {
      return num.doubleValue();
    }
    final JSONString str = value.isString();
    if (str != null) {
      try {
        return Double.parseDouble(str.stringValue());
      } catch (final NumberFormatException e) {
        throw new IllegalArgumentException("String value is not parsable as double: [" + str.stringValue() + "]", e);
      }
    }
    throw new IllegalArgumentException("JSONValue is neither JSONNumber nor JSONString, cannot parse as number.");
  }
}
