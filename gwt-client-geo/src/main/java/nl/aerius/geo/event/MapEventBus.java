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

import java.util.HashSet;
import java.util.Set;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.event.shared.binder.impl.GenericEventType;

public class MapEventBus extends SimpleEventBus {
  private static Set<String> unscoped = new HashSet<>();

  private final EventBus wrapped;
  private final Set<HandlerRegistration> registrations = new HashSet<>();
  private String scopeId;

  public MapEventBus(final EventBus wrappedBus) {
    this.wrapped = wrappedBus;
  }

  public MapEventBus(final EventBus wrappedBus, final String scopeId) {
    this.wrapped = wrappedBus;
    this.scopeId = scopeId;
  }

  public static void registerUnscoped(final Class<?> clazz) {
    unscoped.add(clazz.getCanonicalName());
  }

  @Override
  public <H> HandlerRegistration addHandler(final Type<H> type, final H handler) {
    if (type instanceof GenericEventType) {
      final Class<Object> reverseType = GenericEventType.getReverseType((GenericEventType) type);
      if (unscoped.contains(reverseType.getCanonicalName())) {
        return wrapped.addHandler(type, handler);
      }
    }

    return wrapped.addHandlerToSource(type, getScopeId(), handler);
  }

  @Override
  public <H> HandlerRegistration addHandlerToSource(final Event.Type<H> type, final Object source, final H handler) {
    final HandlerRegistration rtn = wrapped.addHandlerToSource(type, source, handler);
    return doRegisterHandler(rtn);
  }

  @Override
  public void fireEvent(final Event<?> event) {
    if (event instanceof Unscoped) {
      wrapped.fireEvent(event);
    } else {
      wrapped.fireEventFromSource(event, getScopeId());
    }
  }

  @Override
  public void fireEventFromSource(final Event<?> event, final Object source) {
    wrapped.fireEventFromSource(event, source);
  }

  /**
   * Visible for testing.
   */
  protected int getRegistrationSize() {
    return registrations.size();
  }

  private HandlerRegistration doRegisterHandler(final HandlerRegistration registration) {
    registrations.add(registration);
    return () -> doUnregisterHandler(registration);
  }

  private void doUnregisterHandler(final HandlerRegistration registration) {
    if (registrations.contains(registration)) {
      registration.removeHandler();
      registrations.remove(registration);
    }
  }

  public void setScopeId(final String scopeId) {
    this.scopeId = scopeId;
  }

  public String getScopeId() {
    return scopeId;
  }

  @Override
  public String toString() {
    return "MapEventBus [scopeId=" + scopeId + "]";
  }
}
