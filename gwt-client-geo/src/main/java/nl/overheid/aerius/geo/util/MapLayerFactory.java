package nl.overheid.aerius.geo.util;

import nl.overheid.aerius.geo.domain.IsLayer;
import nl.overheid.aerius.geo.domain.layer.LayerConfig;

public interface MapLayerFactory<L> {
  IsLayer<L> prepareLayer(LayerConfig c);
}
