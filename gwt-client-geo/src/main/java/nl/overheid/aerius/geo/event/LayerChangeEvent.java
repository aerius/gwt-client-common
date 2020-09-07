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
package nl.overheid.aerius.geo.event;

import nl.overheid.aerius.geo.domain.IsLayer;
import nl.overheid.aerius.wui.event.SimpleGenericEvent;

public abstract class LayerChangeEvent extends SimpleGenericEvent<IsLayer<?>> implements HasLayerChange {
  /**
   * A type of change.
   */
  public static enum CHANGE {
    /**
     * Layer has been added to the layer panel.
     */
    ADDED,
    /**
     * Layer has been disabled.
     */
    DISABLED,
    /**
     * Layer has been enabled.
     */
    ENABLED,
    /**
     * Conditions changed such that the layer has no data to display and has been hidden.
     */
    HIDDEN,
    /**
     * Layer name changed.
     */
    NAME,
    /**
     * Layer opacity changed.
     */
    OPACITY,
    /**
     * Layer order changed in the layer panel.
     */
    ORDER,
    /**
     * Layer has been removed from the layer panel.
     */
    REMOVED,
    /**
     * Conditions changed such that data for the layer is now available and the layer has become visible.
     */
    VISIBLE,

    /**
     * The legend for this layer changed.
     */
    LEGEND_CHANGED;
  }

  private final CHANGE change;

  /**
   * Creates a {@link LayerChangeEvent}.
   *
   * @param layer the Layer to show/hide
   * @param change layer change event
   */
  public LayerChangeEvent(final IsLayer<?> layer, final CHANGE change) {
    super(layer);
    this.change = change;
  }

  @SuppressWarnings("unchecked")
  public <T> T getLayer() {
    return ((IsLayer<T>) getValue()).asLayer();
  }

  /**
   * Returns the layer change event.
   *
   * @return change event
   */
  @Override
  public CHANGE getChange() {
    return change;
  }

  @Override
  public String toString() {
    return "LayerChangeEvent [change=" + change + "]";
  }
}