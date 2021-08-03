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
package nl.aerius.geo.wui;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Prop;
import com.axellience.vuegwt.core.annotations.component.Watch;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasMounted;
import com.google.gwt.user.client.DOM;
import com.google.web.bindery.event.shared.EventBus;

import jsinterop.base.Js;

import nl.aerius.geo.domain.IsMapCohort;
import nl.aerius.geo.event.MapEventBus;

@Component(name = "ol3-map")
public class OL3MapComponent implements Map, HasMounted, IsVueComponent {
  @Data String uniqueId = "openlayers-3-map-auto-generated-" + DOM.createUniqueId();

  @Inject MapLayoutPanel map;

  @Prop EventBus eventBus;

  private MapEventBus mapEventBus;

  private final List<IsMapCohort> cohorts = new ArrayList<>();

  @Watch(value = "eventBus", isImmediate = true)
  public void watchMessage(final EventBus newValue, final EventBus oldValue) {
    setEventBus(newValue);
  }

  @Override
  public void setEventBus(final EventBus eventBus) {
    this.mapEventBus = new MapEventBus(eventBus, uniqueId);
    map.setEventBus(this.mapEventBus);
  }

  @Override
  public void mounted() {
    attach();
  }

  @Override
  public void attach() {
    map.setTarget(uniqueId);
  }

  @Override
  public void setUniqueId(final String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String getUniqueId() {
    return uniqueId;
  }

  @Override
  public void registerEventCohort(final IsMapCohort cohort) {
    if (mapEventBus != null) {
      cohort.notifyMap(this, mapEventBus);
    }

    cohorts.add(cohort);
  }

  public MapEventBus getMapEventBus() {
    return mapEventBus;
  }

  @Override
  public <T> T getMap() {
    return Js.cast(map.getMap());
  }
}
