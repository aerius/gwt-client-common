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
package nl.aerius.wui.place;

import java.util.Map;

public class AbstractTokenizer {
  protected boolean booleanValue(final Map<String, String> tokens, final String key) {
    return tokens.containsKey(key);
  }

  protected int intValue(final Map<String, String> tokens, final String key) {
    return intValue(tokens, key, 0);
  }

  protected int intValue(final Map<String, String> tokens, final String key, final int defaultValue) {
    if (tokens.containsKey(key)) {
      final String value = tokens.get(key);
      try {
        return value == null ? defaultValue : Integer.parseInt(value);
      } catch (final NumberFormatException e) {
        return defaultValue;
      }
    }
    return defaultValue;
  }

  protected void put(final Map<String, String> tokens, final String key) {
    tokens.put(key, null);
  }

  protected void put(final Map<String, String> tokens, final String key, final Integer value) {
    if (value != null) {
      tokens.put(key, String.valueOf(value));
    }
  }

  protected void put(final Map<String, String> tokens, final String key, final int value) {
    tokens.put(key, String.valueOf(value));
  }

  protected void put(final Map<String, String> tokens, final String key, final String value) {
    tokens.put(key, value);
  }

  protected void put(final Map<String, String> tokens, final String key, final Double value) {
    if (value != null) {
      tokens.put(key, String.valueOf(value));
    }
  }

  protected void put(final Map<String, String> tokens, final String key, final double value) {
    tokens.put(key, String.valueOf(value));
  }
}
