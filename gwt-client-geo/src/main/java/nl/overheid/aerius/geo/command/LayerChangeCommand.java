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
package nl.overheid.aerius.geo.command;

import nl.overheid.aerius.geo.domain.IsLayer;
import nl.overheid.aerius.geo.event.HasLayerChange;
import nl.overheid.aerius.geo.event.LayerChangeEvent;
import nl.overheid.aerius.geo.event.LayerChangeEvent.CHANGE;
import nl.overheid.aerius.wui.command.SimpleGenericCommand;

public abstract class LayerChangeCommand<E extends LayerChangeEvent> extends SimpleGenericCommand<IsLayer<?>, E> implements HasLayerChange {
  private final CHANGE change;

  /**
   * Creates a {@link LayerChangeCommand}.
   *
   * @param change layer change event
   */
  public LayerChangeCommand(final IsLayer<?> layer, final CHANGE change) {
    super(layer);
    this.change = change;
  }

  @SuppressWarnings("unchecked")
  public <T> T getLayer() {
    return (T) getIsLayer().asLayer();
  }

  @SuppressWarnings("unchecked")
  public <T> IsLayer<T> getIsLayer() {
    return (IsLayer<T>) getValue();
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
}