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
package nl.aerius.wui.activity;

import nl.aerius.wui.place.Place;

/**
 * Finds the activity to run for a given {@link Place}, used to configure an {@link ActivityManager}.
 */
public interface ActivityMapper<C> {
  /**
   * Returns the activity to run for the given {@link Place}, or null.
   *
   * @param place a Place object
   */
  Activity<?, C> getActivity(Place place);
}
