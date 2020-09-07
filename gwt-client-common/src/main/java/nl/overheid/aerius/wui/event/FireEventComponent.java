package nl.overheid.aerius.wui.event;

import nl.overheid.aerius.wui.command.SimpleGenericCommand;

public class FireEventComponent extends BasicEventComponent {
  protected <T> void fireIfChanged(final SimpleGenericEvent<T> event, final T previous, final T current) {
    if (previous == null) {
      if (current == null) {
        return;
      } else {
        fire(event, current);
        return;
      }
    }

    if (!previous.equals(current)) {
      fire(event, current);
      return;
    }
  }

  protected <T extends Object> void fire(final SimpleGenericEvent<T> event, final T current) {
    // If the event to fire is a command - silence it.
    if (event instanceof SimpleGenericCommand) {
      ((SimpleGenericCommand<?, ?>) event).setSilent(true);
    }

    event.setValue(current);
    eventBus.fireEvent(event);
  }
}
