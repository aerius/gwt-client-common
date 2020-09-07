package nl.overheid.aerius.geo;

import com.google.inject.Inject;

import nl.overheid.aerius.geo.command.InfoLocationChangeCommand;
import nl.overheid.aerius.geo.event.InfoLocationChangeEvent;
import nl.overheid.aerius.geo.event.MapEventBus;
import nl.overheid.aerius.geo.util.MapLayerUtil;

public class GeoInitializer {
  @Inject
  public GeoInitializer(final MapLayerUtil layerUtil) {
    MapEventBus.registerUnscoped(InfoLocationChangeCommand.class);
    MapEventBus.registerUnscoped(InfoLocationChangeEvent.class);
  }
}
