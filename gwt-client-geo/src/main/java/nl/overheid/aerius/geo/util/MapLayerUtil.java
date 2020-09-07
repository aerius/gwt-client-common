package nl.overheid.aerius.geo.util;

import com.google.inject.Inject;

import nl.overheid.aerius.geo.domain.IsLayer;
import nl.overheid.aerius.geo.domain.layer.LayerConfig;

public class MapLayerUtil {
  public static MapLayerUtil I;

  private final MapLayerFactory<?> util;

  @Inject
  public MapLayerUtil(final MapLayerFactory<?> util) {
    this.util = util;

    I = this;
  }

  public static IsLayer<?> prepareLayer(final LayerConfig conf) {
    return I.util.prepareLayer(conf);
  }
}
