package nl.overheid.aerius.geo.wui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import ol.Extent;
import ol.Map;
import ol.format.Wkt;
import ol.geom.Geometry;
import ol.layer.Layer;
import ol.proj.Projection;

import nl.overheid.aerius.geo.command.LayerAddedCommand;
import nl.overheid.aerius.geo.command.LayerHiddenCommand;
import nl.overheid.aerius.geo.command.LayerOpacityCommand;
import nl.overheid.aerius.geo.command.LayerRemovedCommand;
import nl.overheid.aerius.geo.command.LayerVisibleCommand;
import nl.overheid.aerius.geo.command.MapResizeCommand;
import nl.overheid.aerius.geo.domain.IsLayer;
import nl.overheid.aerius.geo.epsg.EPSG;
import nl.overheid.aerius.geo.event.LayerHiddenEvent;
import nl.overheid.aerius.geo.event.LayerOpacityEvent;
import nl.overheid.aerius.geo.event.LayerVisibleEvent;
import nl.overheid.aerius.geo.event.MapSetExtentEvent;
import nl.overheid.aerius.geo.wui.util.OL3MapUtil;
import nl.overheid.aerius.wui.command.Command;
import nl.overheid.aerius.wui.event.HasEventBus;

public class MapLayoutPanel implements HasEventBus {
  interface MapLayoutPanelEventBinder extends EventBinder<MapLayoutPanel> {}

  private final MapLayoutPanelEventBinder EVENT_BINDER = GWT.create(MapLayoutPanelEventBinder.class);

  private final EPSG epsg;
  private Map map;
  private EventBus eventBus;

  private String deferredExtent;

  private boolean deferredZoomScheduled;
  private boolean defferedSizeUpdateScheduled;

  @Inject
  public MapLayoutPanel(final EPSG epsg) {
    this.epsg = epsg;

    OL3MapUtil.prepareEPSG(epsg);
  }

  @Override
  public void setEventBus(final EventBus eventBus) {
    this.eventBus = eventBus;

    EVENT_BINDER.bindEventHandlers(this, eventBus);
  }

  public void setTarget(final String uniqueId) {
    final Projection projection = Projection.get(epsg.getEpsgCode());
    if (projection == null) {
      throw new RuntimeException("Projection not available while this is mandatory. " + epsg.getEpsgCode());
    }

    map = OL3MapUtil.prepareMap(uniqueId, projection);
    OL3MapUtil.prepareControls(map);

    // Base layer preparation.
    final IsLayer<Layer> baseLayer = OL3MapUtil.prepareBaseLayerDefault(map, projection, epsg);
    eventBus.fireEvent(new LayerAddedCommand(baseLayer));
    updateSize();
  }

  @EventHandler
  void onMapResizeCommand(final MapResizeCommand c) {
    updateSize();
  }

  @EventHandler
  void onMapSetExtentEvent(final MapSetExtentEvent c) {
    final String wkt = c.getValue().replaceAll("BOX", "LINESTRING");

    deferZoomToExtent(wkt);
  }

  private void deferZoomToExtent(final String wkt) {
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

      map.getView().fit(extent);

      deferredZoomScheduled = false;
    });
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
  void onLayerOpacityCommand(final LayerOpacityCommand c) {
    setLayerOpacity(c.getIsLayer(), c.getOpacity());
  }

  private void finishCommand(final Command<?> c, final boolean success) {
    if (!success) {
      c.silence();
    }
  }

  /**
   * To be used only as a delegate method from a command handler and not directly.
   */
  private boolean addLayer(final IsLayer<Layer> layer) {
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
  private boolean removeLayer(final IsLayer<Layer> layer) {
    return removeLayer(layer.asLayer());
  }

  /**
   * To be used only as a delegate method from a command handler and not directly.
   */
  private boolean setLayerVisible(final IsLayer<Layer> isLayer, final boolean visible) {
    if (isLayer.asLayer().getVisible() == visible) {
      return false;
    }

    isLayer.asLayer().setVisible(visible);

    return true;
  }

  private void setLayerOpacity(final IsLayer<Layer> isLayer, final double opacity) {
    isLayer.asLayer().setOpacity(opacity);
  }

  /**
   * To be used only as a delegate method from an command handler and not
   * directly.
   */
  private boolean addLayer(final Layer baseLayer) {
    map.addLayer(baseLayer);

    // TODO Implement action indication
    return true;
  }

  /**
   * To be used only as a delegate method from an command handler and not
   * directly.
   */
  private boolean removeLayer(final Layer baseLayer) {
    map.removeLayer(baseLayer);

    // TODO Implement action indication
    return true;
  }

  public void updateSize() {
    if (defferedSizeUpdateScheduled) {
      return;
    }

    defferedSizeUpdateScheduled = true;
    Scheduler.get().scheduleDeferred(() -> {
      if (map == null) {
        return;
      }

      map.updateSize();
      defferedSizeUpdateScheduled = false;
    });
  }

  public Map getMap() {
    return map;
  }
}
