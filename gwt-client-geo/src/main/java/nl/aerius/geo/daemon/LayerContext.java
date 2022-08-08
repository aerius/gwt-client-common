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
package nl.aerius.geo.daemon;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.google.inject.Singleton;

import jsinterop.annotations.JsProperty;

import nl.aerius.geo.domain.BundledLayerItem;
import nl.aerius.geo.domain.IsLayer;
import nl.aerius.geo.domain.LayerItem;
import nl.aerius.geo.domain.SimpleLayerItem;

@Singleton
public class LayerContext {
  @JsProperty private final Map<String, LayerItem> layerItems = new HashMap<>();

  @JsProperty private final Map<IsLayer<?>, LayerItem> layers = new HashMap<>();

  public void add(final IsLayer<?> layer) {
    layer.getInfoOptional().ifPresent(info -> {
      // FIXME There's some functionality overlap between key determination and bundle
      // determination.
      // Resolving that will make configuration errors less likely.
      final String key = Optional.ofNullable(info.getBundle()).orElse(info.getName());
      final boolean bundle = info.getBundle() != null && !info.getBundle().isEmpty();

      // Create the layer item if it does not yet exist
      layerItems.putIfAbsent(key, bundle ? new BundledLayerItem() : new SimpleLayerItem());

      // Install the layer into it
      final LayerItem layerItem = layerItems.compute(key, (k, item) -> {
        item.install(layer);
        return item;
      });

      // Register the layer
      layers.put(layer, layerItem);
    });
  }

  public Collection<LayerItem> getLayerItems() {
    return layerItems.values();
  }

  public void remove(final IsLayer<?> value) {
    layers.remove(value);
  }

  public void setOpacity(final IsLayer<?> layer, final double opacity) {
    if (layers.containsKey(layer)) {
      layers.get(layer).setOpacity(layer, opacity);
    }
  }

  public void setVisible(final IsLayer<?> layer, final boolean visible) {
    if (layers.containsKey(layer)) {
      layers.get(layer).setVisible(layer, visible);
    }
  }
}
