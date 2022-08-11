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
package nl.aerius.geo.event;

import nl.aerius.geo.domain.IsOverlay;
import nl.aerius.wui.event.SimpleGenericEvent;

public abstract class OverlayChangeEvent extends SimpleGenericEvent<IsOverlay<?>> implements HasOverlayChange {

  /**
   * A type of change.
   */
  public static enum Change {
    ADDED,
    REMOVED
  }

  private final Change change;

  /**
   * Creates a {@link OverlayChangeEvent}.
   *
   * @param overlay the interaction
   * @param change  interaction change type
   */
  public OverlayChangeEvent(final IsOverlay<?> overlay, final Change change) {
    super(overlay);
    this.change = change;
  }

  @SuppressWarnings("unchecked")
  public <T> T getOverlay() {
    return ((IsOverlay<T>) getValue()).asOverlay();
  }

  /**
   * Returns the overlay change event.
   *
   * @return change event
   */
  @Override
  public Change getChange() {
    return change;
  }

  @Override
  public String toString() {
    return "InteractionChangeEvent [change=" + change + "]";
  }
}
