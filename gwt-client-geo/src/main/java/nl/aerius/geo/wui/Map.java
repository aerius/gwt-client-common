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
package nl.aerius.geo.wui;

import nl.aerius.geo.domain.HasMapCohort;
import nl.aerius.geo.domain.IsMapCohort;
import nl.aerius.wui.event.HasEventBus;

public interface Map extends HasEventBus {
  void attach();

  void registerEventCohort(IsMapCohort mapCohort);

  default void registerEventCohort(final HasMapCohort mapCohort) {
    registerEventCohort(mapCohort.asMapCohort());
  }

  String getUniqueId();

  void setUniqueId(String uniqueId);

  <T> T getMap();
}
