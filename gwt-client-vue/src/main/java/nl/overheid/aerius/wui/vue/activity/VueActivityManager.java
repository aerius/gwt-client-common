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
package nl.overheid.aerius.wui.vue.activity;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import nl.overheid.aerius.wui.activity.AbstractActivityManager;
import nl.overheid.aerius.wui.activity.ActivityMapper;
import nl.overheid.aerius.wui.place.PlaceController;
import nl.overheid.aerius.wui.vue.AcceptsOneComponent;

public class VueActivityManager extends AbstractActivityManager<AcceptsOneComponent> {
  @Inject
  public VueActivityManager(final EventBus globalEventBus, final PlaceController placeController, final ActivityMapper<AcceptsOneComponent> mapper) {
    super(globalEventBus, placeController, mapper);
  }
}
