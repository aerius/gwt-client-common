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
package nl.aerius.geo.wui.layers;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import ol.Collection;
import ol.Coordinate;
import ol.Feature;
import ol.FeatureOptions;
import ol.OLFactory;
import ol.format.Wkt;
import ol.format.WktReadOptions;
import ol.layer.Layer;
import ol.layer.VectorLayerOptions;
import ol.proj.Projection;
import ol.source.Vector;
import ol.source.VectorOptions;
import ol.style.Icon;
import ol.style.IconOptions;
import ol.style.Style;
import ol.style.StyleOptions;

import nl.aerius.geo.domain.InformationZoomLevel;
import nl.aerius.geo.domain.IsLayer;
import nl.aerius.geo.domain.Point;
import nl.aerius.geo.event.InfoLocationChangeEvent;
import nl.aerius.geo.util.HexagonUtil;

public class InformationLayer implements IsLayer<Layer> {
  private static final String INFO_MARKER_SVG = "data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 32 32' width='32' height='32'><title>an-location</title><path d='M17,3.64a8.39,8.39,0,0,0-8.57,8.58c0,2.84,1.37,5,3,7.43a33.2,33.2,0,0,1,4.38,9.77,1.26,1.26,0,0,0,2.45,0,33,33,0,0,1,4.38-9.77c1.59-2.46,3-4.59,3-7.43A8.38,8.38,0,0,0,17,3.64ZM17,17.1a4.87,4.87,0,1,1,4.87-4.87A4.87,4.87,0,0,1,17,17.1Z' fill='#d63327'/><circle cx='17' cy='12.23' r='2.39' fill='#d63327'/></svg>";
  private final InformationLayerEventBinder EVENT_BINDER = GWT.create(InformationLayerEventBinder.class);

  interface InformationLayerEventBinder extends EventBinder<InformationLayer> {}

  private final ol.layer.Vector vectorLayer;
  private final Projection projection;

  public InformationLayer(final Projection projection, final EventBus eventBus) {
    this.projection = projection;
    // create style
    final StyleOptions styleOptions = new StyleOptions();

    final IconOptions iconOptions = new IconOptions();
    iconOptions.setSrc(INFO_MARKER_SVG);
    iconOptions.setSnapToPixel(true);
    iconOptions.setAnchor(new double[] {0.5, 1});
    // iconOptions.setImgSize(OLFactory.createSize(40, 20));
    final Icon icon = new Icon(iconOptions);
    styleOptions.setImage(icon);
    styleOptions.setStroke(OLFactory.createStroke(OLFactory.createColor(214, 51, 39, 1), 2));
    styleOptions.setFill(OLFactory.createFill(OLFactory.createColor(255, 255, 255, 0.4)));

    final Style style = new Style(styleOptions);

    final VectorLayerOptions vectorLayerOptions = OLFactory.createOptions();
    vectorLayerOptions.setStyle(style);

    vectorLayer = new ol.layer.Vector(vectorLayerOptions);
    vectorLayer.setZIndex(10001);

    EVENT_BINDER.bindEventHandlers(this, eventBus);
  }

  @EventHandler
  public void onInformationLocationChangedEvent(final InfoLocationChangeEvent e) {
    // Create the final hexagon feature
    final Feature hexagon = createHexagon(e.getValue());

    // Create the marker
    final Feature marker = createMarker(e.getValue());

    // Push the features
    final Collection<Feature> features = new Collection<Feature>();
    features.push(marker);
    features.push(hexagon);

    // Create source
    final VectorOptions vectorSourceOptions = OLFactory.createOptions();
    vectorSourceOptions.setFeatures(features);
    final Vector vectorSource = new Vector(vectorSourceOptions);

    // Update layer with new source
    vectorLayer.setSource(vectorSource);
  }

  private Feature createMarker(final Point value) {
    final Coordinate coordinate = OLFactory.createCoordinate(value.getX(), value.getY());
    final ol.geom.Point point = new ol.geom.Point(coordinate);
    final FeatureOptions featureOptions = OLFactory.createOptions();
    featureOptions.setGeometry(point);
    return new Feature(featureOptions);
  }

  private Feature createHexagon(final Point value) {
    final String hexagonWKT = HexagonUtil.createHexagonWkt(value, InformationZoomLevel.get());
    final Wkt wkt = new Wkt();
    final WktReadOptions wktOptions = OLFactory.createOptions();
    wktOptions.setDataProjection(projection);
    wktOptions.setFeatureProjection(projection);
    return wkt.readFeature(hexagonWKT, wktOptions);
  }

  @Override
  public Layer asLayer() {
    return vectorLayer;
  }
}
