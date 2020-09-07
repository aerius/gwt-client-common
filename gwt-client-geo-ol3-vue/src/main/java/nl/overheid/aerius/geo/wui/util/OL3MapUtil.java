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
package nl.overheid.aerius.geo.wui.util;

import ol.Collection;
import ol.Coordinate;
import ol.Extent;
import ol.Map;
import ol.MapOptions;
import ol.OLFactory;
import ol.View;
import ol.ViewOptions;
import ol.control.Control;
import ol.control.MousePosition;
import ol.control.ScaleLine;
import ol.control.Zoom;
import ol.control.ZoomSlider;
import ol.layer.Layer;
import ol.layer.LayerOptions;
import ol.layer.Tile;
import ol.proj.Projection;
import ol.proj.ProjectionOptions;
import ol.source.Wmts;
import ol.source.WmtsOptions;
import ol.tilegrid.WmtsTileGrid;
import ol.tilegrid.WmtsTileGridOptions;

import nl.overheid.aerius.geo.BBox;
import nl.overheid.aerius.geo.domain.IsLayer;
import nl.overheid.aerius.geo.domain.LayerInfo;
import nl.overheid.aerius.geo.epsg.EPSG;
import nl.overheid.aerius.geo.wui.layers.OL3Layer;

/**
 * BOILERPLATE CONTAINER
 */
public final class OL3MapUtil {
  private OL3MapUtil() {}

  public static Map prepareMap(final String target, final Projection projection) {
    // create a view
    final ViewOptions viewOptions = OLFactory.createOptions();
    viewOptions.setProjection(projection);
    final View view = new View(viewOptions);

    final Coordinate centerCoordinate = OLFactory.createCoordinate(142892.19D, 470783.87D);

    view.setCenter(centerCoordinate);
    view.setZoom(3);
    view.setMinZoom(2);
    view.setMaxZoom(14);

    // create the map
    final MapOptions mapOptions = OLFactory.createOptions();
    mapOptions.setTarget(target);
    mapOptions.setView(view);
    mapOptions.setControls(new Collection<Control>());

    return new ol.Map(mapOptions);
  }

  /**
   * Creates a WMTS tilegrid.
   *
   * @param projection
   *          projection of the grid
   * @return WMTS tilegrid
   */
  private static WmtsTileGrid createWmtsTileGrid(final Projection projection) {
    final WmtsTileGridOptions wmtsTileGridOptions = OLFactory.createOptions();

    final double[] resolutions = new double[14];
    final String[] matrixIds = new String[14];

    final double width = projection.getExtent().getWidth();
    final double matrixWidth = width / 256;

    for (int i = 0; i < 14; i++) {
      resolutions[i] = matrixWidth / Math.pow(2, i);
      matrixIds[i] = String.valueOf(i);
    }

    final Coordinate tileGridOrigin = projection.getExtent().getTopLeft();
    wmtsTileGridOptions.setOrigin(tileGridOrigin);
    wmtsTileGridOptions.setResolutions(resolutions);
    wmtsTileGridOptions.setMatrixIds(matrixIds);

    return new WmtsTileGrid(wmtsTileGridOptions);
  }

  public static IsLayer<Layer> prepareBaseLayerDefault(final Map map, final Projection projection, final EPSG epsg) {
    final WmtsOptions wmtsOptions = OLFactory.createOptions();
    // https://geodata.nationaalgeoregister.nl/tiles/service/wmts?request=GetCapabilities&service=WMTS
    wmtsOptions.setUrl("https://geodata.nationaalgeoregister.nl/tiles/service/wmts/");
    wmtsOptions.setLayer("brtachtergrondkaartgrijs");
    wmtsOptions.setFormat("image/png8");
    wmtsOptions.setMatrixSet(epsg.getEpsgCode());
    wmtsOptions.setStyle("default");
    wmtsOptions.setProjection(projection);
    wmtsOptions.setWrapX(true);
    wmtsOptions.setTileGrid(createWmtsTileGrid(projection));

    final Wmts wmtsSource = new Wmts(wmtsOptions);

    final LayerOptions wmtsLayerOptions = OLFactory.createOptions();
    wmtsLayerOptions.setSource(wmtsSource);

    final Tile wmtsLayer = new Tile(wmtsLayerOptions);
    wmtsLayer.setOpacity(1);
    wmtsLayer.setVisible(true);

    final LayerInfo info = new LayerInfo();
    info.setTitle("Achtergrondkaart");
    info.setName("0");
    info.setBundle("baseLayer");

    return wrap(wmtsLayer, info);
  }

  public static IsLayer<Layer> prepareBaseLayerColoured(final Map map, final Projection projection, final EPSG epsg) {
    final WmtsOptions wmtsOptions = OLFactory.createOptions();
    // https://geodata.nationaalgeoregister.nl/tiles/service/wmts?request=GetCapabilities&service=WMTS
    wmtsOptions.setUrl("https://geodata.nationaalgeoregister.nl/tiles/service/wmts/");
    wmtsOptions.setLayer("brtachtergrondkaart");
    wmtsOptions.setFormat("image/png8");
    wmtsOptions.setMatrixSet(epsg.getEpsgCode());
    wmtsOptions.setStyle("default");
    wmtsOptions.setProjection(projection);
    wmtsOptions.setWrapX(true);
    wmtsOptions.setTileGrid(createWmtsTileGrid(projection));

    final Wmts wmtsSource = new Wmts(wmtsOptions);

    final LayerOptions wmtsLayerOptions = OLFactory.createOptions();
    wmtsLayerOptions.setSource(wmtsSource);

    final Tile wmtsLayer = new Tile(wmtsLayerOptions);
    wmtsLayer.setOpacity(1);
    wmtsLayer.setVisible(false);

    final LayerInfo info = new LayerInfo();
    info.setTitle("Achtergrondkaart (Kleur)");
    info.setName("1");
    info.setBundle("baseLayer");

    return wrap(wmtsLayer, info);
  }

  public static IsLayer<Layer> prepareBaseLayerWater(final Map map, final Projection projection, final EPSG epsg) {
    final WmtsOptions wmtsOptions = OLFactory.createOptions();
    // https://geodata.nationaalgeoregister.nl/tiles/service/wmts?request=GetCapabilities&service=WMTS
    wmtsOptions.setUrl("https://geodata.nationaalgeoregister.nl/tiles/service/wmts/");
    wmtsOptions.setLayer("brtachtergrondkaartwater");
    wmtsOptions.setFormat("image/png8");
    wmtsOptions.setMatrixSet(epsg.getEpsgCode());
    wmtsOptions.setStyle("default");
    wmtsOptions.setProjection(projection);
    wmtsOptions.setWrapX(true);
    wmtsOptions.setTileGrid(createWmtsTileGrid(projection));

    final Wmts wmtsSource = new Wmts(wmtsOptions);

    final LayerOptions wmtsLayerOptions = OLFactory.createOptions();
    wmtsLayerOptions.setSource(wmtsSource);

    final Tile wmtsLayer = new Tile(wmtsLayerOptions);
    wmtsLayer.setOpacity(1);
    wmtsLayer.setVisible(false);

    final LayerInfo info = new LayerInfo();
    info.setTitle("Achtergrondkaart (Water)");
    info.setName("2");
    info.setBundle("baseLayer");

    return wrap(wmtsLayer, info);
  }

  public static IsLayer<Layer> prepareBaseLayerPastel(final Map map, final Projection projection, final EPSG epsg) {
    final WmtsOptions wmtsOptions = OLFactory.createOptions();
    // https://geodata.nationaalgeoregister.nl/tiles/service/wmts?request=GetCapabilities&service=WMTS
    wmtsOptions.setUrl("https://geodata.nationaalgeoregister.nl/tiles/service/wmts/");
    wmtsOptions.setLayer("brtachtergrondkaartpastel");
    wmtsOptions.setFormat("image/png8");
    wmtsOptions.setMatrixSet(epsg.getEpsgCode());
    wmtsOptions.setStyle("default");
    wmtsOptions.setProjection(projection);
    wmtsOptions.setWrapX(true);
    wmtsOptions.setTileGrid(createWmtsTileGrid(projection));

    final Wmts wmtsSource = new Wmts(wmtsOptions);

    final LayerOptions wmtsLayerOptions = OLFactory.createOptions();
    wmtsLayerOptions.setSource(wmtsSource);

    final Tile wmtsLayer = new Tile(wmtsLayerOptions);
    wmtsLayer.setOpacity(1);
    wmtsLayer.setVisible(false);

    final LayerInfo info = new LayerInfo();
    info.setTitle("Achtergrondkaart (Pastel)");
    info.setName("3");
    info.setBundle("baseLayer");

    return wrap(wmtsLayer, info);
  }

  public static final IsLayer<Layer> prepareBasePhotoLayer(final Map map, final Projection projection, final EPSG epsg) {
    final WmtsOptions wmtsOptions = OLFactory.createOptions();
    wmtsOptions.setUrl("https://geodata.nationaalgeoregister.nl/luchtfoto/rgb/wmts");
    wmtsOptions.setLayer("Actueel_ortho25");
    wmtsOptions.setFormat("image/png");
    wmtsOptions.setMatrixSet(epsg.getEpsgCode());
    wmtsOptions.setStyle("default");
    wmtsOptions.setProjection(projection);
    wmtsOptions.setWrapX(true);
    wmtsOptions.setTileGrid(createWmtsTileGrid(projection));

    final Wmts wmtsSource = new Wmts(wmtsOptions);

    final LayerOptions wmtsLayerOptions = OLFactory.createOptions();
    wmtsLayerOptions.setSource(wmtsSource);

    final Tile wmtsLayer = new Tile(wmtsLayerOptions);
    wmtsLayer.setOpacity(1);
    wmtsLayer.setVisible(false);

    final LayerInfo info = new LayerInfo();
    info.setTitle("Luchtfoto (PDOK)");
    info.setName("4");
    info.setBundle("baseLayer");

    return wrap(wmtsLayer, info);
  }

  public static void prepareControls(final Map map) {
    // add some controls
    map.addControl(new Zoom());
    map.addControl(new ZoomSlider());
    map.addControl(new ScaleLine());
    // map.addControl(new OverviewMap());
    final MousePosition mousePosition = new MousePosition();
    mousePosition.setCoordinateFormat(Coordinate.createStringXY(0));
    map.addControl(mousePosition);
  }

  public static void prepareEPSG(final EPSG epsg) {
    final BBox bnds = epsg.getBounds();
    final ProjectionOptions options = new ProjectionOptions();
    options.setCode(epsg.getEpsgCode());
    options.setUnits(epsg.getUnit());
    options.setExtent(new Extent(bnds.getMinX(), bnds.getMinY(), bnds.getMaxX(), bnds.getMaxY()));
    Projection.addProjection(new Projection(options));
  }

  public static IsLayer<Layer> wrap(final Layer layer) {
    return new OL3Layer(layer);
  }

  public static IsLayer<Layer> wrap(final Layer layer, final LayerInfo info) {
    return new OL3Layer(layer, info);
  }

  public static IsLayer<Layer> wrap(final Layer layer, final LayerInfo info, final Runnable retirement) {
    return new OL3Layer(layer, info, retirement);
  }
}
