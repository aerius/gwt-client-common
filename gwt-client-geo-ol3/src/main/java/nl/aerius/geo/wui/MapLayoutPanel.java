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

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import ol.Extent;
import ol.Map;
import ol.format.Wkt;
import ol.geom.Geometry;
import ol.geom.Polygon;
import ol.layer.Layer;
import ol.proj.Projection;

import nl.aerius.geo.BBox;
import nl.aerius.geo.command.InformationLayerActiveCommand;
import nl.aerius.geo.command.LayerAddedCommand;
import nl.aerius.geo.command.LayerHiddenCommand;
import nl.aerius.geo.command.LayerOpacityCommand;
import nl.aerius.geo.command.LayerRemovedCommand;
import nl.aerius.geo.command.LayerVisibleCommand;
import nl.aerius.geo.command.MapResizeCommand;
import nl.aerius.geo.command.MapSetExtentCommand;
import nl.aerius.geo.domain.IsLayer;
import nl.aerius.geo.epsg.EPSG;
import nl.aerius.geo.event.LayerHiddenEvent;
import nl.aerius.geo.event.LayerOpacityEvent;
import nl.aerius.geo.event.LayerVisibleEvent;
import nl.aerius.geo.event.MapSetExtentEvent;
import nl.aerius.geo.event.MapSetShortcircuitedExtentEvent;
import nl.aerius.geo.event.RequestExtentCorrectionEvent;
import nl.aerius.geo.util.ReceptorUtil;
import nl.aerius.geo.wui.util.OL3MapUtil;
import nl.aerius.wui.command.Command;
import nl.aerius.wui.event.HasEventBus;

public class MapLayoutPanel implements HasEventBus {
  interface MapLayoutPanelEventBinder extends EventBinder<MapLayoutPanel> {}

  private final MapLayoutPanelEventBinder EVENT_BINDER = GWT.create(MapLayoutPanelEventBinder.class);

  private final EPSG epsg;
  private Map map;
  private EventBus eventBus;
  private final ReceptorUtil receptorUtil;

  private String deferredExtent;

  private boolean deferredZoomScheduled;
  private boolean defferedSizeUpdateScheduled;

  private final Timer userInitiatedExtentPersistTimer = new Timer() {
    @Override
    public void run() {
      final Wkt wkt = new Wkt();
      final Polygon poly = Polygon.fromExtent(map.getView().calculateExtent(map.getSize()));
      final String wktString = wkt.writeGeometry(poly);

      eventBus.fireEvent(new MapSetExtentCommand(wktString, false, true, true));
    }
  };

  private boolean attachInformationLayer;

  @Inject
  public MapLayoutPanel(final EPSG epsg, final ReceptorUtil receptorUtil) {
    this.epsg = epsg;
    this.receptorUtil = receptorUtil;

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

    map.getView().addChangeListener(e -> notifyUserInitiatedChangeExtent());

    // Base layer preparation.
    final IsLayer<Layer> baseLayer = OL3MapUtil.prepareBaseLayerDefault(map, projection, epsg);
    final IsLayer<Layer> baseLayerColoured = OL3MapUtil.prepareBaseLayerColoured(map, projection, epsg);
    final IsLayer<Layer> baseLayerWater = OL3MapUtil.prepareBaseLayerWater(map, projection, epsg);
    final IsLayer<Layer> baseLayerPastel = OL3MapUtil.prepareBaseLayerPastel(map, projection, epsg);
    final IsLayer<Layer> photoLayer = OL3MapUtil.prepareBasePhotoLayer(map, projection, epsg);

    Scheduler.get().scheduleDeferred(() -> {
      eventBus.fireEvent(new LayerAddedCommand(baseLayer));
      eventBus.fireEvent(new LayerAddedCommand(baseLayerColoured));
      eventBus.fireEvent(new LayerAddedCommand(baseLayerWater));
      eventBus.fireEvent(new LayerAddedCommand(baseLayerPastel));
      eventBus.fireEvent(new LayerAddedCommand(photoLayer));
      if (attachInformationLayer) {
        attachInformationLayer();
      }

      updateSize();
    });
  }

  private void notifyUserInitiatedChangeExtent() {
    userInitiatedExtentPersistTimer.schedule(350);
  }

  @EventHandler
  void onInformationLayerActiveCommand(final InformationLayerActiveCommand c) {
    if (map != null) {
      attachInformationLayer();
    } else {
      attachInformationLayer = true;
    }
  }

  private void attachInformationLayer() {
    final IsLayer<Layer> infoLayer = OL3MapUtil.prepareInformationLayer(map, Projection.get(epsg.getEpsgCode()), eventBus, receptorUtil);
    eventBus.fireEvent(new LayerAddedCommand(infoLayer));

  }

  @EventHandler
  void onMapResizeCommand(final MapResizeCommand c) {
    updateSize();
  }

  @EventHandler
  void onMapSetShortcircuitedExtentEvent(final MapSetShortcircuitedExtentEvent e) {
    final String wkt = e.getValue().replaceAll("BOX", "LINESTRING");

    deferZoomToExtent(wkt, true);
  }

  @EventHandler
  void onMapSetExtentEvent(final MapSetExtentEvent e) {
    final String wkt = e.getValue().replaceAll("BOX", "LINESTRING");

    deferZoomToExtent(wkt, false);
  }

  private void deferZoomToExtent(final String wkt, final boolean shortCircuit) {
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

      if (shortCircuit) {
//        GWTProd.log("MAP", "Setting extent to (via shortcircuit): " + extent);
        map.getView().fit(extent);
      } else {
        final Extent result = fitCorrectedExtent(extent);

        final Wkt wktLocal = new Wkt();
        final Polygon poly = Polygon.fromExtent(result);
        final String wktString = wktLocal.writeGeometry(poly);

//        GWTProd.log("MAP", "Persisting extent: " + wktString);
        eventBus.fireEvent(new MapSetExtentCommand(wktString, false, true, true));
      }

      deferredZoomScheduled = false;
    });
  }

  private Extent fitCorrectedExtent(final Extent extent) {
    final BBox box = new BBox(extent.getLowerLeftX(), extent.getLowerLeftY(), extent.getUpperRightX(), extent.getUpperRightY());

    final RequestExtentCorrectionEvent ev = new RequestExtentCorrectionEvent(box, map.getViewport());
    eventBus.fireEvent(ev);

    final BBox correctedBox = ev.getCorrectedBox();
    final Extent correctedExtent = new Extent(correctedBox.getMinX(), correctedBox.getMinY(), correctedBox.getMaxX(), correctedBox.getMaxY());

//    GWTProd.log("MAP", "Setting extent to: " + correctedExtent);
    map.getView().fit(correctedExtent);
    return correctedExtent;
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
  private boolean addLayer(final Layer layer) {
    map.addLayer(layer);

    // TODO Implement action indication
    return true;
  }

  /**
   * To be used only as a delegate method from an command handler and not
   * directly.
   */
  private boolean removeLayer(final Layer layer) {
    map.removeLayer(layer);

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
