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

import javax.inject.Inject;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Prop;
import com.axellience.vuegwt.core.annotations.component.Watch;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasMounted;
import com.google.gwt.user.client.DOM;
import com.google.web.bindery.event.shared.EventBus;

import elemental2.dom.DomGlobal;
import elemental2.dom.Element;

import nl.aerius.geo.command.MapAttachCommand;
import nl.aerius.geo.command.MapDetachCommand;
import nl.aerius.wui.util.SchedulerUtil;

@Component(name = "ol3-map")
public class OL3MapComponent implements Map, IsVueComponent, HasMounted {
  @Data String uniqueId = "openlayers-map-" + DOM.createUniqueId();

  @Inject MapLayoutPanel map;

  @Prop EventBus eventBus;

  @Watch(value = "eventBus", isImmediate = true)
  public void watchMessage(final EventBus newValue, final EventBus oldValue) {
    setEventBus(newValue);
  }

  @Override
  public void mounted() {
    SchedulerUtil.delay(() -> {
      final Element zoomSlider = DomGlobal.document.querySelector(".ol-zoomslider-thumb");
      if (zoomSlider != null) {
        zoomSlider.setAttribute("aria-label", "Slide to zoom");
      }
    });
  }

  @Override
  public void setEventBus(final EventBus eventBus) {
    map.setEventBus(eventBus);
  }

  @Override
  public void attach() {
    eventBus.fireEvent(new MapAttachCommand(uniqueId));
  }

  @Override
  public void detach() {
    eventBus.fireEvent(new MapDetachCommand());
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
  public Object getInnerMap() {
    return map.getInnerMap();
  }
}
