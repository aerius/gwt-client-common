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

import java.util.stream.IntStream;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import ol.Coordinate;
import ol.Extent;
import ol.Map;
import ol.OLFactory;
import ol.Overlay;
import ol.ViewFitOptions;
import ol.animation.AnimationOptions;
import ol.easing.Easing;
import ol.format.Wkt;
import ol.geom.Geometry;
import ol.interaction.Interaction;
import ol.layer.Base;

import nl.aerius.geo.command.InteractionAddedCommand;
import nl.aerius.geo.command.InteractionRemoveCommand;
import nl.aerius.geo.command.LayerAddedCommand;
import nl.aerius.geo.command.LayerHiddenCommand;
import nl.aerius.geo.command.LayerOpacityCommand;
import nl.aerius.geo.command.LayerRemovedCommand;
import nl.aerius.geo.command.LayerVisibilityToggleCommand;
import nl.aerius.geo.command.LayerVisibleCommand;
import nl.aerius.geo.command.MapAttachCommand;
import nl.aerius.geo.command.MapCenterChangeCommand;
import nl.aerius.geo.command.MapDetachCommand;
import nl.aerius.geo.command.MapPanDownCommand;
import nl.aerius.geo.command.MapPanLeftCommand;
import nl.aerius.geo.command.MapPanRightCommand;
import nl.aerius.geo.command.MapPanUpCommand;
import nl.aerius.geo.command.MapResizeCommand;
import nl.aerius.geo.command.MapResizeSequenceCommand;
import nl.aerius.geo.command.MapSetExtentCommand;
import nl.aerius.geo.command.MapZoomInCommand;
import nl.aerius.geo.command.MapZoomOutCommand;
import nl.aerius.geo.command.OverlayAddedCommand;
import nl.aerius.geo.command.OverlayRemoveCommand;
import nl.aerius.geo.command.RenderSyncCommand;
import nl.aerius.geo.domain.IsInteraction;
import nl.aerius.geo.domain.IsLayer;
import nl.aerius.geo.domain.IsOverlay;
import nl.aerius.geo.event.LayerHiddenEvent;
import nl.aerius.geo.event.LayerOpacityEvent;
import nl.aerius.geo.event.LayerVisibleEvent;
import nl.aerius.geo.event.MapCenterChangeEvent;
import nl.aerius.geo.event.MapChangeEvent;
import nl.aerius.geo.event.MapRenderCompleteEvent;
import nl.aerius.geo.event.MapSetExtentEvent;
import nl.aerius.geo.wui.util.OL3MapUtil;
import nl.aerius.wui.command.Command;
import nl.aerius.wui.event.HasEventBus;
import nl.aerius.wui.util.SchedulerUtil;
import nl.aerius.shared.domain.geo.EPSG;

/**
 * Manages the events to a Openlayers map.
 */
public class MapLayoutPanel implements HasEventBus {
  private static final int PAN_AMOUNT = 100;
  private static final int ZOOM_ANIMATION_DURATION = 400;

  interface MapLayoutPanelEventBinder extends EventBinder<MapLayoutPanel> {}

  private final MapLayoutPanelEventBinder EVENT_BINDER = GWT.create(MapLayoutPanelEventBinder.class);

  private Map map;
  private EventBus eventBus;
  private String deferredExtent;

  private boolean deferredZoomScheduled;
  private HandlerRegistration handlers;

  /**
   * Constructs the map for the given projection.
   *
   * This method should be called once and would have done in the constructor if
   * epsg would not be dynamic. If epsg will be made static, as in available at
   * startup, this code could be moved to a constructor.
   *
   * @param epsg projection.
   */
  public void init(final EPSG epsg) {
    map = OL3MapUtil.prepareMap(epsg);
    Window.addResizeHandler(v -> updateSize());
    map.on("precompose", event -> eventBus.fireEvent(new MapChangeEvent()));
    map.on("rendercomplete", event -> eventBus.fireEvent(new MapRenderCompleteEvent()));
  }

  @Override
  public void setEventBus(final EventBus eventBus) {
    this.eventBus = eventBus;

    if (handlers != null) {
      handlers.removeHandler();
    }
    handlers = EVENT_BINDER.bindEventHandlers(this, eventBus);
  }

  @EventHandler
  void onMapAttachCommand(final MapAttachCommand c) {
    map.setTarget(c.getValue());
    updateSize();

    SchedulerUtil.delay(() -> {
      final Coordinate center = map.getView().getCenter();

      eventBus.fireEvent(new MapCenterChangeCommand(center.getX(), center.getY()));
    });
  }

  @EventHandler
  void onMapDetachCommand(final MapDetachCommand c) {
    map.setTarget((String) null);
  }

  @EventHandler
  void onMapCenterChangeEvent(final MapCenterChangeEvent e) {
    map.getView().setCenter(new Coordinate(e.getX(), e.getY()));
    if (e.getZoomLevel() > 0) {
      map.getView().setZoom(e.getZoomLevel());
    }
  }

  @EventHandler
  void onMapResizeSequenceCommand(final MapResizeSequenceCommand c) {
    updateSize();
    final int totalTime = 500;
    final int interval = 5;
    IntStream.range(0, totalTime / interval)
        .forEach(i -> SchedulerUtil.delay(() -> updateSize(), i * interval));
  }

  @EventHandler
  void onMapResizeCommand(final MapResizeCommand c) {
    updateSize();
  }

  @EventHandler
  void onMapSetExtentCommand(final MapSetExtentCommand c) {
    c.silence();
    final String wkt = c.getValue().replace("BOX", "LINESTRING");

    deferZoomToExtent(wkt, c.getValue(), c.isAnimated());
  }

  @EventHandler
  void onLayerAddedCommand(final LayerAddedCommand c) {
    finishCommand(c, addLayer(c.getIsLayer()));
  }

  @EventHandler
  void onLayerRemovedCommand(final LayerRemovedCommand c) {
    finishCommand(c, removeLayer(c.getIsLayer()));
  }

  @EventHandler
  void onLayerVisibleCommand(final LayerVisibleCommand c) {
    finishCommand(c, setLayerVisible(c.getIsLayer(), true));
  }

  @EventHandler
  void onLayerHiddenCommand(final LayerHiddenCommand c) {
    finishCommand(c, setLayerVisible(c.getIsLayer(), false));
  }

  @EventHandler
  void onLayerVisibilityToggleCommand(final LayerVisibilityToggleCommand c) {
    eventBus.fireEvent(isLayerVisible(c.getIsLayer())
        ? new LayerHiddenCommand(c.getValue())
        : new LayerVisibleCommand(c.getValue()));
  }

  @EventHandler
  void onLayerOpacityCommand(final LayerOpacityCommand c) {
    setLayerOpacity(c.getIsLayer(), c.getOpacity());
  }

  @EventHandler
  void onOverlayAddedCommand(final OverlayAddedCommand c) {
    finishCommand(c, addOverlay(c.getIsOverlay()));
  }

  @EventHandler
  void onOverlayRemovedCommand(final OverlayRemoveCommand c) {
    finishCommand(c, removeOverlay(c.getIsOverlay()));
  }

  @EventHandler
  void onInteractionAddedCommand(final InteractionAddedCommand c) {
    finishCommand(c, addInteraction(c.getIsInteraction()));
  }

  @EventHandler
  void onInteractionRemovedCommand(final InteractionRemoveCommand c) {
    finishCommand(c, removeInteraction(c.getIsInteraction()));
  }

  @EventHandler
  void onMapZoomInCommand(final MapZoomInCommand c) {
    final AnimationOptions options = OLFactory.createOptions();
    options.setCenter(map.getView().getCenter());
    options.setZoom(map.getView().getZoom() + 1);
    defaultAnimate(options);
  }

  @EventHandler
  void onMapZoomOutCommand(final MapZoomOutCommand c) {
    final AnimationOptions options = OLFactory.createOptions();
    options.setCenter(map.getView().getCenter());
    options.setZoom(map.getView().getZoom() - 1);
    defaultAnimate(options);
  }

  @EventHandler
  void onMapPanRightCommand(final MapPanRightCommand c) {
    onMapPanCommand(PAN_AMOUNT, 0);
  }

  @EventHandler
  void onMapPanLeftCommand(final MapPanLeftCommand c) {
    onMapPanCommand(-PAN_AMOUNT, 0);
  }

  @EventHandler
  void onMapPanUpCommand(final MapPanUpCommand c) {
    onMapPanCommand(0, PAN_AMOUNT);
  }

  @EventHandler
  void onMapPanDownCommand(final MapPanDownCommand c) {
    onMapPanCommand(0, -PAN_AMOUNT);
  }

  void onMapPanCommand(final int deltaX, final int deltaY) {
    final Coordinate center = map.getView().getCenter();
    final double resolution = map.getView().getResolution();

    final Coordinate newCenter = new Coordinate(center.getX() + deltaX * resolution, center.getY() + deltaY * resolution);
    final AnimationOptions options = OLFactory.createOptions();
    options.setCenter(newCenter);

    defaultAnimate(options);
  }

  @EventHandler
  public void onRenderSyncCommand(final RenderSyncCommand c) {
    map.renderSync();
    if (c.getValue() != null) {
      map.once("rendercomplete", event -> {
        c.getValue().run();
      });
    }
  }

  private void defaultAnimate(final AnimationOptions options) {
    options.setEasing(Easing.easeOut());
    options.setDuration(200);
    map.getView().animate(options);
  }

  private void finishCommand(final Command<?> c, final boolean success) {
    if (!success) {
      c.silence();
    }
  }

  /**
   * To be used only as a delegate method from a command handler and not directly.
   */
  private boolean addInteraction(final IsInteraction<Interaction> interaction) {
    return addInteraction(interaction.asInteraction());
  }

  /**
   * To be used only as a delegate method from a command handler and not directly.
   */
  private boolean removeInteraction(final IsInteraction<Interaction> interaction) {
    return removeInteraction(interaction.asInteraction());
  }

  /**
   * To be used only as a delegate method from a command handler and not directly.
   */
  private boolean addOverlay(final IsOverlay<Overlay> interaction) {
    return addOverlay(interaction.asOverlay());
  }

  /**
   * To be used only as a delegate method from a command handler and not directly.
   */
  private boolean removeOverlay(final IsOverlay<Overlay> interaction) {
    return removeOverlay(interaction.asOverlay());
  }

  /**
   * To be used only as a delegate method from a command handler and not directly.
   */
  private boolean addLayer(final IsLayer<Base> layer) {
    final boolean visible = layer.asLayer().getVisible();
    Scheduler.get().scheduleDeferred(() -> {
      eventBus.fireEvent(visible ? new LayerVisibleEvent(layer) : new LayerHiddenEvent(layer));
      eventBus.fireEvent(new LayerOpacityEvent(layer, layer.asLayer().getOpacity()));
    });

    return addLayer(layer.asLayer());
  }

  /**
   * To be used only as a delegate method from a command handler and not directly.
   */
  private boolean removeLayer(final IsLayer<Base> layer) {
    return removeLayer(layer.asLayer());
  }

  private boolean isLayerVisible(final IsLayer<Base> isLayer) {
    return isLayer.asLayer().getVisible();
  }

  /**
   * To be used only as a delegate method from a command handler and not directly.
   */
  private boolean setLayerVisible(final IsLayer<Base> isLayer, final boolean visible) {
    if (isLayer.asLayer().getVisible() == visible) {
      return false;
    }

    isLayer.asLayer().setVisible(visible);

    return true;
  }

  private void setLayerOpacity(final IsLayer<Base> isLayer, final double opacity) {
    isLayer.asLayer().setOpacity(opacity);
  }

  /**
   * To be used only as a delegate method from an command handler and not
   * directly.
   */
  private boolean addLayer(final Base layer) {
    map.addLayer(layer);

    // TODO Implement action indication
    return true;
  }

  /**
   * To be used only as a delegate method from an command handler and not
   * directly.
   */
  private boolean removeLayer(final Base layer) {
    map.removeLayer(layer);

    // TODO Implement action indication
    return true;
  }

  /**
   * To be used only as a delegate method from an command handler and not
   * directly.
   */
  private boolean addInteraction(final Interaction interaction) {
    map.addInteraction(interaction);
    return true;
  }

  /**
   * To be used only as a delegate method from an command handler and not
   * directly.
   */
  private boolean removeInteraction(final Interaction interaction) {
    map.removeInteraction(interaction);
    return true;
  }

  /**
   * To be used only as a delegate method from an command handler and not
   * directly.
   */
  private boolean addOverlay(final Overlay overlay) {
    map.addOverlay(overlay);
    return true;
  }

  /**
   * To be used only as a delegate method from an command handler and not
   * directly.
   */
  private boolean removeOverlay(final Overlay overlay) {
    map.removeOverlay(overlay);
    return true;
  }

  private void deferZoomToExtent(final String wkt, final String originalWkt, final boolean isAnimated) {
    deferredExtent = wkt;
    if (deferredZoomScheduled) {
      return;
    }

    deferredZoomScheduled = true;
    Scheduler.get().scheduleDeferred(() -> {
      if (map == null) {
        return;
      }

      final Wkt format = new Wkt();
      final Geometry geom = format.readGeometry(deferredExtent);
      final Extent extent = geom.getExtent();

      final ViewFitOptions options = OLFactory.createOptions();
      if (isAnimated) {
        options.setDuration(ZOOM_ANIMATION_DURATION);
      }
      options.setMaxZoom(10);

      map.getView().fit(extent, options);

      deferredZoomScheduled = false;

      SchedulerUtil.delay(() -> eventBus.fireEvent(new MapSetExtentEvent(originalWkt)), isAnimated ? ZOOM_ANIMATION_DURATION : 0);
    });
  }

  public Map getInnerMap() {
    return map;
  }

  public void updateSize() {
    map.updateSize();
  }
}
