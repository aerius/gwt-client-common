package nl.overheid.aerius.wui.place;

import java.util.ArrayList;
import java.util.List;

/**
 * Tokenizer to work provide a helper to url parameters with parameters with &
 * and = dividers like key1=value&key2=value.
 *
 * @param <P> specific place this works on
 */
public abstract class SequenceTokenizer<P extends ApplicationPlace> implements PlaceTokenizer<P> {
  private static final String DIVIDER = "/";

  @Override
  public final P getPlace(final String token) {
    final List<String> values = new ArrayList<String>();

    final String[] args = token.split(DIVIDER);
    for (int i = 0; i < args.length; i++) {
      values.add(args[i]);
    }

    final P place = createPlace();
    updatePlace(values, place);
    return place;
  }

  @Override
  public final String getToken(final P place) {
    final List<String> tokens = new ArrayList<String>();

    setTokenList(place, tokens);
    final StringBuilder sb = new StringBuilder();
    for (final String value : tokens) {
      sb.append(value);
      sb.append(DIVIDER);
    }

    return sb.toString();
  }

  /**
   * Returns a new instance of the place.
   *
   * @return new place instance
   */
  protected abstract P createPlace();

  /**
   * In the implementation place any additional values present in the tokens in
   * the given place object and
   *
   * @param tokens
   * @return
   */
  protected void updatePlace(final List<String> tokens, final P place) {}

  /**
   * In the implementation add additional values from the place into the token
   * map.
   *
   * @param place  place object
   * @param tokens token list
   */
  protected void setTokenList(final P place, final List<String> tokens) {}
}
