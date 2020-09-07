package nl.overheid.aerius.wui.event;

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
