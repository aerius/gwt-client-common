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
package nl.overheid.aerius.geo.wui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import jsinterop.base.Js;

import nl.overheid.aerius.geo.domain.IsMapCohort;
import nl.overheid.aerius.geo.event.MapEventBus;

public class OL3Map extends Composite implements Map {
  private String uniqueId;

  private final MapLayoutPanel map;

  private boolean attached;

  private MapEventBus eventBus;

  private final List<IsMapCohort> cohorts = new ArrayList<>();

  @Inject
  public OL3Map(final MapLayoutPanel map) {
    this.map = map;
    final SimplePanel container = new SimplePanel();
    container.getElement().getStyle().setPosition(Position.RELATIVE);

    initWidget(container);

    container.getElement().getStyle().setWidth(100, Unit.PCT);
    container.getElement().getStyle().setHeight(100, Unit.PCT);

    container.getElement().getStyle().setCursor(Cursor.POINTER);

    uniqueId = "openlayers-3-map-auto-generated-" + DOM.createUniqueId();
  }

  @Override
  public void setEventBus(final EventBus eventBus) {
    getElement().setId(uniqueId);
    final MapEventBus mapEventBus = new MapEventBus(eventBus, uniqueId);
    this.eventBus = mapEventBus;
    map.setEventBus(mapEventBus);


    notifyCohorts();
  }

  @Override
  protected void onLoad() {
    if (attached) {
      map.updateSize();
    }
  }

  @Override
  public void attach() {
    if (attached) {
      return;
    }

    map.setTarget(uniqueId);

    attached = true;
  }

  private void notifyCohorts() {
    for (final IsMapCohort cohort : cohorts) {
      cohort.notifyMap(this, eventBus);
    }
  }

  @Override
  public void registerEventCohort(final IsMapCohort cohort) {
    if (eventBus != null) {
      cohort.notifyMap(this, eventBus);
    }

    cohorts.add(cohort);
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
  public <T> T getMap() {
    return Js.cast(map.getMap());
  }
}
