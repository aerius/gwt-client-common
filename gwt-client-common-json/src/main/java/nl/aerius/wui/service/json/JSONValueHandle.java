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

import com.google.gwt.json.client.JSONValue;

public class JSONValueHandle {
  private final JSONValue inner;

  public JSONValueHandle(final JSONValue inner) {
    this.inner = inner;
  }

  public JSONValue getInner() {
    return inner;
  }

  public boolean isString() {
    return inner != null && inner.isString() != null;
  }

  public String asString() {
    if (inner == null || inner.isString() == null) {
      throw new IllegalStateException("Value is not a String");
    }
    return inner.isString().stringValue();
  }

  /**
   * Returns the integer value of this JSONValue.
   * Delegates to asNumber() and casts the result to int.
   * 
   * @return The integer value.
   * @throws IllegalStateException if the value cannot be converted to a number.
   */
  public Integer asInteger() {
    // Rely on asNumber for parsing logic (including strings like "Infinity")
    // Note: Casting Infinity/NaN to int results in Integer.MAX_VALUE/MIN_VALUE/0
    // respectively.
    return asNumber().intValue();
  }

  /**
   * Returns the double value of this JSONValue.
   * Uses JsonNumericParser to handle JSONNumber and special strings ("Infinity",
   * etc.).
   *
   * @return The double value.
   * @throws IllegalStateException if the value cannot be converted to a number.
   */
  public Double asNumber() {
    try {
      return JsonNumericParser.parseAsNumber(this.inner);
    } catch (final IllegalArgumentException e) {
      // Convert to IllegalStateException for consistency with other 'as' methods
      throw new IllegalStateException(
          "Cannot convert value to number: " + (this.inner != null ? this.inner.toString() : "null"), e);
    }
  }

  /**
   * Returns the long value of this JSONValue.
   * Delegates to asNumber() and casts the result to long.
   * 
   * @return The long value.
   * @throws IllegalStateException if the value cannot be converted to a number.
   */
  public Long asLong() {
    // Rely on asNumber for parsing logic (including strings like "Infinity")
    // Note: Casting Infinity/NaN to long results in Long.MAX_VALUE/MIN_VALUE/0
    // respectively.
    return asNumber().longValue();
  }


  public boolean isObject() {
    return inner != null && inner.isObject() != null;
  }

  public JSONObjectHandle asObjectHandle() {
    if (inner == null || inner.isObject() == null) {
      throw new IllegalStateException("Value is not an Object");
    }
    return new JSONObjectHandle(inner.isObject());
  }

  public boolean isArray() {
    return inner != null && inner.isArray() != null;
  }

  public JSONArrayHandle asArray() {
    if (inner == null || inner.isArray() == null) {
      throw new IllegalStateException("Value is not an Array");
    }
    return new JSONArrayHandle(inner.isArray());
  }

  /**
   * Checks if this JSONValue represents a number.
   * Delegates to JsonNumericParser.isRepresentingNumber.
   *
   * @return true if the value is a JSONNumber or a special FP string, false
   *         otherwise.
   */
  public boolean isNumber() {
    return JsonNumericParser.isRepresentingNumber(this.inner);
  }

  public boolean isBoolean() {
    return inner != null && inner.isBoolean() != null;
  }

  public boolean asBoolean() {
    if (inner == null || inner.isBoolean() == null) {
      throw new IllegalStateException("Value is not a Boolean");
    }
    return inner.isBoolean().booleanValue();
  }

  public boolean isNull() {
    return inner == null || inner.isNull() != null;
  }

}
