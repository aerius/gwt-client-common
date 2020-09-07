package nl.overheid.aerius.wui.activity;

import com.google.web.bindery.event.shared.EventBus;

import nl.overheid.aerius.wui.event.HasEventBus;

public abstract class EventActivity<P, V extends View<P>> extends AbstractActivity<P, V> implements HasEventBus {
  private final HasEventBus[] eventChildren;

  protected EventBus eventBus;

  public EventActivity(final V view, final HasEventBus... eventChildren) {
    super(view);

    this.eventChildren = eventChildren;
  }

  @Override
  public void setEventBus(final EventBus eventBus) {
    this.eventBus = eventBus;

    if (view instanceof HasEventBus) {
      ((HasEventBus) view).setEventBus(eventBus);
    }

    for (final HasEventBus child : eventChildren) {
      child.setEventBus(eventBus);
    }
  }
}
