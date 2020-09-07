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
package nl.overheid.aerius.wui.command;

import nl.overheid.aerius.wui.event.SimpleGenericEvent;

/**
 * @param <T> The value to transform [Transform]
 * @param <M> The transformed value [Morphed]
 * @param <E> The event containing the transformed/morphed value [Event]
 */
public abstract class ProxyCommand<T, M, E extends SimpleGenericEvent<M>> extends SimpleGenericEvent<T> implements Command<E> {
  private boolean silent;

  private E event;

  public ProxyCommand() {}

  public ProxyCommand(final T value) {
    super(value);
  }

  public ProxyCommand(final T value, final boolean silent) {
    super(value);

    this.silent = silent;
  }

  @Override
  public E getEvent() {
    if (event == null) {
      throw new RuntimeException(
          "Proxy command (" + getClass().getSimpleName() + ") was not handled while this is mandatory; Command's Event is not set.");
    }

    return event;
  }

  public void setEvent(final E event) {
    this.event = event;
  }

  @Override
  public boolean isSilent() {
    return silent;
  }

  @Override
  public void setSilent(final boolean silent) {
    this.silent = silent;
  }
}
