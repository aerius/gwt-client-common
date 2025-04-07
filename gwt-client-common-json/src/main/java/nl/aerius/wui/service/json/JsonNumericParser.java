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