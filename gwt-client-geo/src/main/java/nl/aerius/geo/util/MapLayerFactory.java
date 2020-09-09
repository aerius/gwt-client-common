package nl.aerius.geo.util;

import nl.aerius.geo.domain.IsLayer;
import nl.aerius.geo.domain.layer.LayerConfig;

public interface MapLayerFactory<L> {
  IsLayer<L> prepareLayer(LayerConfig c);
}
