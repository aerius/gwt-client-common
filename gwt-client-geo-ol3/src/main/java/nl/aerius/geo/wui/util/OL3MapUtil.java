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
package nl.aerius.geo.wui.util;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ol.Collection;
import ol.Coordinate;
import ol.Extent;
import ol.GenericFunction;
import ol.Map;
import ol.MapOptions;
import ol.OLFactory;
import ol.View;
import ol.ViewOptions;
import ol.control.Attribution;
import ol.control.AttributionOptions;
import ol.control.Control;
import ol.control.MousePosition;
import ol.control.ScaleLine;
import ol.control.Zoom;
import ol.control.ZoomSlider;
import ol.layer.Layer;
import ol.proj.Projection;

import nl.aerius.geo.domain.IsLayer;
import nl.aerius.geo.domain.LayerInfo;
import nl.aerius.geo.shared.LayerProps;
import nl.aerius.geo.shared.MapProperties;
import nl.aerius.geo.wui.layers.OL3Layer;
import nl.overheid.aerius.geo.shared.BBox;

/**
 * Utility class to initialize map.
 */
public final class OL3MapUtil {
  private static final int INITIAL_ZOOM = 3;

  private OL3MapUtil() {}

  public static Map prepareMap(final MapProperties mapProps) {
    final Projection projection = supplementEPSG(mapProps);
    // create a view
    final ViewOptions viewOptions = OLFactory.createOptions();
    viewOptions.setProjection(projection);
    final View view = new View(viewOptions);

    final Coordinate centerCoordinate = OLFactory.createCoordinate(mapProps.getCenterX(), mapProps.getCenterY());

    view.setCenter(centerCoordinate);
    view.setZoom(INITIAL_ZOOM);
    view.setMinZoom(1);
    view.setMaxZoom(mapProps.getMaxZoomLevel());

    // create the map
    final MapOptions mapOptions = OLFactory.createOptions();
    mapOptions.setView(view);
    mapOptions.setControls(new Collection<Control>());
    final ol.Map map = new ol.Map(mapOptions);

    prepareControls(map, mapProps);
    return map;
  }

  private static void prepareControls(final Map map, final MapProperties mapProps) {
    // add some controls
    map.addControl(new Zoom());
    map.addControl(new ZoomSlider());
    map.addControl(new ScaleLine());
    final AttributionOptions attributionOptions = new AttributionOptions();
    attributionOptions.setCollapsible(false);
    attributionOptions.setCollapsed(false);
    map.addControl(new Attribution(attributionOptions));

    final MousePosition mousePosition = new MousePosition();

    final GenericFunction<Coordinate, String> coordinatesFormat = s -> mapProps.getCoordinatesPrefix() + Coordinate.createStringXY(0).call(s);
    mousePosition.setCoordinateFormat(coordinatesFormat);
    map.addControl(mousePosition);
  }

  private static Projection supplementEPSG(final MapProperties mapProps) {
    // NOTE: The given epsg must be defined elsewhere through proj4
    final Projection projection = Projection.get(mapProps.getEpsg());
    if (projection == null) {
      throw new RuntimeException("Unknown EPSG encountered: " + mapProps.getEpsg());
    }

    final BBox bnds = mapProps.getBounds();
    projection.setExtent(new Extent(bnds.getMinX(), bnds.getMinY(), bnds.getMaxX(), bnds.getMaxY()));

    return projection;
  }

  public static List<IsLayer<?>> createLayers(final List<LayerProps> layers, final Consumer<String> layerErrorHandler) {
    final OL3MapLayerFactory layerFactory = new OL3MapLayerFactory(layerErrorHandler);

    // TODO: Bundle layers first

    return layers.stream()
        .map(layer -> layerFactory.prepareLayer(layer))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  public static IsLayer<Layer> wrap(final Layer layer) {
    return new OL3Layer(layer);
  }

  public static IsLayer<Layer> wrap(final Layer layer, final LayerInfo info) {
    return new OL3Layer(layer, info);
  }
}
