package nl.aerius.geo;

import com.google.inject.Inject;

import nl.aerius.geo.command.InfoLocationChangeCommand;
import nl.aerius.geo.event.InfoLocationChangeEvent;
import nl.aerius.geo.event.MapEventBus;
import nl.aerius.geo.util.MapLayerUtil;

public class GeoInitializer {
  @Inject
  public GeoInitializer(final MapLayerUtil layerUtil) {
    MapEventBus.registerUnscoped(InfoLocationChangeCommand.class);
    MapEventBus.registerUnscoped(InfoLocationChangeEvent.class);
  }
}
