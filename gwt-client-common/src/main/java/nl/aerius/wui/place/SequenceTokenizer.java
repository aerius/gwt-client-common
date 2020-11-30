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
