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

public abstract class ParameteredTokenizer<P extends TokenizedPlace> extends AbstractTokenizer {
  /**
   * Returns a new instance of the place.
   *
   * @return new place instance
   */
  protected abstract P createPlace();

  /**
   * In the implementation place any additional key values present in the tokens in the given place object and
   *
   * @param tokens
   * @return
   */
  protected void updatePlace(final Map<String, String> tokens, final P place) {}

  /**
   * In the implementation add additional key/values from the place into the token map.
   *
   * @param place
   *          place object
   * @param tokens
   *          token map
   */
  protected void setTokenMap(final P place, final Map<String, String> tokens) {}
}
