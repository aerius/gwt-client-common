package nl.overheid.aerius.wui.place;

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
