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
package nl.aerius.wui.event;

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.binder.EventBinder;

public class BasicEventComponent implements HasEventBus {
  protected EventBus eventBus;

  protected <T> HandlerRegistration bindEventHandlers(final T thiz, final EventBinder<T> binder) {
    return binder.bindEventHandlers(thiz, eventBus);
  }

  @Override
  public void setEventBus(final EventBus eventBus) {
    this.eventBus = eventBus;
  }

  public <T> HandlerRegistration setEventBus(final EventBus eventBus, final T thiz, final EventBinder<T> binder,
      final HasEventBus... children) {
    setEventBus(eventBus, children);
    return bindEventHandlers(thiz, binder);
  }

  public void setEventBus(final EventBus eventBus, final HasEventBus... children) {
    this.eventBus = eventBus;

    for (final HasEventBus child : children) {
      child.setEventBus(eventBus);
    }
  }
}
