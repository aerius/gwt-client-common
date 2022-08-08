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
package nl.aerius.geo.command;

import nl.aerius.geo.domain.IsOverlay;
import nl.aerius.geo.event.HasOverlayChange;
import nl.aerius.geo.event.OverlayChangeEvent;
import nl.aerius.wui.command.SimpleGenericCommand;

public abstract class OverlayChangeCommand<E extends OverlayChangeEvent> extends SimpleGenericCommand<IsOverlay<?>, E>
    implements HasOverlayChange {
  private final OverlayChangeEvent.Change change;

  /**
   * Creates a {@link OverlayChangeCommand}.
   *
   * @param change layer change event
   */
  public OverlayChangeCommand(final IsOverlay<?> overlay, final OverlayChangeEvent.Change change) {
    super(overlay);
    this.change = change;
  }

  @SuppressWarnings("unchecked")
  public <T> T getOverlay() {
    return (T) getIsOverlay().asOverlay();
  }

  @SuppressWarnings("unchecked")
  public <T> IsOverlay<T> getIsOverlay() {
    return (IsOverlay<T>) getValue();
  }

  /**
   * Returns the layer change event.
   *
   * @return change event
   */
  @Override
  public OverlayChangeEvent.Change getChange() {
    return change;
  }

}
