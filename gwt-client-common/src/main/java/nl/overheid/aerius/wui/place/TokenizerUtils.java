/*
 * Copyright Dutch Ministry of Agriculture, Nature and Food Quality
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
package nl.overheid.aerius.wui.place;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class TokenizerUtils {
  private static final String PAIR_DIVIDER = "/";

  private static final String COMPOSITE_DIVIDER = "&";
  private static final String COMPOSITE_EQUALIZER = "=";

  public static final String QUERY_STRING_RAW = "?";
  public static final String QUERY_STRING = "\\" + QUERY_STRING_RAW;

  public static final String SEPARATOR = "/";

  public static Map<String, String> find(final String token) {
    final String[] parts = token.split(QUERY_STRING, 2);

    final Map<String, String> pairs = findPairs(parts[0]);
    final Map<String, String> composites = findComposite(parts[1]);

    final Map<String, String> combined = new HashMap<>(composites);
    // Overwrite with pairs
    combined.putAll(pairs);
    return combined;
  }

  public static Map<String, String> findComposite(final String token) {
    return findPairs(token, COMPOSITE_DIVIDER, COMPOSITE_EQUALIZER);
  }

  public static Map<String, String> findPairs(final String token) {
    return findPairs(token, PAIR_DIVIDER);
  }

  public static final String formatComposite(final Map<String, String> pairs) {
    return formatPairs(pairs, COMPOSITE_DIVIDER, COMPOSITE_EQUALIZER);
  }

  public static final String formatPairs(final Map<String, String> pairs) {
    return formatPairs(pairs, PAIR_DIVIDER, PAIR_DIVIDER);
  }

  public static Map<String, String> findPairs(final String token, final String divider) {
    final Map<String, String> values = new HashMap<String, String>();

    if (token == null || token.isEmpty()) {
      return values;
    }

    final String[] args = token.split(divider);
    for (int i = 0; i < args.length; i += 2) {
      values.put(args[i], args[i + 1]);
    }

    return values;
  }

  public static Map<String, String> findPairs(final String token, final String divider, final String equalizer) {
    final Map<String, String> values = new HashMap<String, String>();

    if (token == null || token.isEmpty()) {
      return values;
    }

    final String[] args = token.split(divider);
    for (int i = 0; i < args.length; i++) {
      final String[] pair = args[i].split(equalizer, 2);
      values.put(pair[0], pair[1]);
    }

    return values;
  }

  public static final String formatPairs(final Map<String, String> pairs, final String divider, final String equalizer) {
    final StringBuilder sb = new StringBuilder();
    for (final Entry<String, String> entry : pairs.entrySet()) {
      if (entry.getValue() == null) {
        continue;
      }

      sb.append(entry.getKey());
      sb.append(equalizer);
      sb.append(entry.getValue());
      sb.append(divider);
    }

    // Remove the last part (divider)
    if (sb.length() != 0) {
      sb.setLength(sb.length() - 1);
    }

    return sb.toString();
  }

  public static String format(final Map<String, String> pairs, final Map<String, String> composites) {
    final String pairToken = formatPairs(pairs);
    final String compositeToken = formatComposite(composites);

    return formatToken(pairToken, compositeToken);
  }

  public static String formatToken(final String base) {
    return base;
  }

  public static String formatToken(final String base, final String query) {
    return base + (query == null || query.isEmpty() ? "" : QUERY_STRING_RAW) + query;
  }
}
