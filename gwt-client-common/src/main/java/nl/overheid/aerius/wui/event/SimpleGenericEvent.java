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
package nl.overheid.aerius.wui.event;

import com.google.gwt.user.client.TakesValue;
import com.google.web.bindery.event.shared.binder.GenericEvent;

/**
 * Simple event, encapsulating a generically typed object.
 *
 * @param <V> The object type this event is wrapping
 */
public abstract class SimpleGenericEvent<V> extends GenericEvent implements TakesValue<V> {
  private V object;

  /**
   * Create a {@link SimpleGenericEvent} with the given object.
   */
  public SimpleGenericEvent() {}

  /**
   * Create a {@link SimpleGenericEvent} with the given object.
   *
   * @param obj object to encapsulate
   */
  public SimpleGenericEvent(final V obj) {
    this.object = obj;
  }

  public V getValue() {
    return object;
  }

  public void setValue(final V current) {
    object = current;
  }
}
