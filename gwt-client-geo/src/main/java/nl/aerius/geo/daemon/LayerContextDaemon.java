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
package nl.aerius.geo.daemon;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import nl.aerius.geo.command.LayerHiddenCommand;
import nl.aerius.geo.domain.IsLayer;
import nl.aerius.wui.event.BasicEventComponent;
import nl.aerius.geo.event.LayerAddedEvent;
import nl.aerius.geo.event.LayerHiddenEvent;
import nl.aerius.geo.event.LayerOpacityEvent;
import nl.aerius.geo.event.LayerRemovedEvent;
import nl.aerius.geo.event.LayerVisibleEvent;

public class LayerContextDaemon extends BasicEventComponent {
  private final LayerContextDaemonEventBinder EVENT_BINDER = GWT.create(LayerContextDaemonEventBinder.class);

  interface LayerContextDaemonEventBinder extends EventBinder<LayerContextDaemon> {}

  @Inject LayerContext layerContext;

  private final Map<String, IsLayer<?>> clusters = new HashMap<>();

  @EventHandler
  public void onLayerAddedEvent(final LayerAddedEvent e) {
    layerContext.add(e.getValue());
  }

  @EventHandler
  public void onLayerRemovedEvent(final LayerRemovedEvent e) {
    layerContext.remove(e.getValue());
  }

  @EventHandler
  public void onLayerOpacityEvent(final LayerOpacityEvent e) {
    layerContext.setOpacity(e.getValue(), e.getOpacity());
  }

  @EventHandler
  public void onLayerVisibleEvent(final LayerVisibleEvent e) {
    final IsLayer<?> layer = e.getValue();
    layerContext.setVisible(layer, true);

    layer.getInfoOptional().flatMap(v -> v.getCluster()).ifPresent(cluster -> {
      clusters.merge(cluster, layer, (a, b) -> {
        eventBus.fireEvent(new LayerHiddenCommand(a));
        return b;
      });
    });
  }

  @EventHandler
  public void onLayerHiddenEvent(final LayerHiddenEvent e) {
    final IsLayer<?> layer = e.getValue();
    layerContext.setVisible(layer, false);
    layer.getInfoOptional().flatMap(v -> v.getCluster()).ifPresent(cluster -> {
      // If present and identical, remove, if not leave untouched
      clusters.computeIfPresent(cluster, (a, b) -> b == e.getValue() ? null : b);
    });
  }

  @Override
  public void setEventBus(final EventBus eventBus) {
    super.setEventBus(eventBus, this, EVENT_BINDER);
  }
}
