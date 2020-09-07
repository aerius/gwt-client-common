package nl.overheid.aerius.geo.event;

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