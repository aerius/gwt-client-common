package nl.overheid.aerius.wui.place;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Tokenizer to work provide a helper to url parameters with parameters with & and = dividers like key1=value&key2=value.
 *
 * @param <P> specific place this works on
 */
public abstract class CompositeTokenizer<P extends TokenizedPlace> extends ParameteredTokenizer<P> implements PlaceTokenizer<P> {
  private static final String ARGUMENT_DIVIDER = "&";
  private static final String VALUE_DIVIDER = "=";

  @Override
  public final P getPlace(final String token) {
    final Map<String, String> values = new HashMap<String, String>();
    final String[] args = token.split(ARGUMENT_DIVIDER);

    for (final String arg : args) {
      final String[] value = arg.split(VALUE_DIVIDER);

      values.put(value[0], value.length > 1 ? value[1] : null);
    }
    final P place = createPlace();
    updatePlace(values, place);
    return place;
  }

  @Override
  public final String getToken(final P place) {
    final Map<String, String> tokens = new HashMap<String, String>();

    setTokenMap(place, tokens);
    final StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (final Entry<String, String> entry : tokens.entrySet()) {
      if (first) {
        first = false;
      } else {
        sb.append(ARGUMENT_DIVIDER);
      }
      sb.append(entry.getKey());
      if (entry.getValue() != null) {
        sb.append(VALUE_DIVIDER);
        sb.append(entry.getValue());
      }
    }
    return sb.toString();
  }
}
