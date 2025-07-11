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
package nl.aerius.wui.history;

import com.google.inject.ImplementedBy;

import nl.aerius.wui.place.TokenizedPlace;

@ImplementedBy(HistoryManagerImpl.class)
public interface HistoryManager {

  /**
   * Determines current place based on history and goes to that place.
   */
  void handleCurrentHistory();

  /**
   * Returns the current place, can be null if no place present.
   *
   * @return place or null if no place present
   */
  TokenizedPlace getCurrentPlace();

  /**
   * Returns the place given the place token if the token could be matched or null otherwise.
   *
   * @param token Place token.
   * @return Place or null if place could not be matched with the token
   */
  TokenizedPlace getPlace(String token);
}
