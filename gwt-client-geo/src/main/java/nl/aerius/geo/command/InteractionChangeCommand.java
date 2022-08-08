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

import nl.aerius.geo.domain.IsInteraction;
import nl.aerius.geo.event.HasInteractionChange;
import nl.aerius.geo.event.InteractionChangeEvent;
import nl.aerius.wui.command.SimpleGenericCommand;

public abstract class InteractionChangeCommand<E extends InteractionChangeEvent> extends SimpleGenericCommand<IsInteraction<?>, E> implements
    HasInteractionChange {
  private final InteractionChangeEvent.Change change;

  /**
   * Creates a {@link InteractionChangeCommand}.
   *
   * @param change layer change event
   */
  public InteractionChangeCommand(final IsInteraction<?> interaction, final InteractionChangeEvent.Change change) {
    super(interaction);
    this.change = change;
  }

  @SuppressWarnings("unchecked")
  public <T> T getInteraction() {
    return (T) getIsInteraction().asInteraction();
  }

  @SuppressWarnings("unchecked")
  public <T> IsInteraction<T> getIsInteraction() {
    return (IsInteraction<T>) getValue();
  }

  /**
   * Returns the layer change event.
   *
   * @return change event
   */
  @Override
  public InteractionChangeEvent.Change getChange() {
    return change;
  }

}
