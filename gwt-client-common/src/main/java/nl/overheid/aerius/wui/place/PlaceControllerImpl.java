package nl.overheid.aerius.wui.place;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import nl.overheid.aerius.wui.command.PlaceChangeCommand;
import nl.overheid.aerius.wui.command.PlaceChangeRequestCommand;

@Singleton
public class PlaceControllerImpl implements PlaceController {
  private final PlaceControllerImplEventBinder EVENT_BINDER = GWT.create(PlaceControllerImplEventBinder.class);

  interface PlaceControllerImplEventBinder extends EventBinder<PlaceControllerImpl> {}

  private final EventBus eventBus;

  private Place previousPlace;
  private Place where;

  @Inject
  public PlaceControllerImpl(final EventBus eventBus) {
    this.eventBus = eventBus;

    EVENT_BINDER.bindEventHandlers(this, eventBus);
  }

  @Override
  public Place getPlace() {
    return where;
  }

  @Override
  public Place getPreviousPlace() {
    return previousPlace == null ? null : previousPlace;
  }

  @EventHandler
  public void onPlaceChangeRequestCommand(final PlaceChangeRequestCommand c) {
    goTo(c.getValue());
  }

  @Override
  public void goTo(final Place place) {
    final Place current = getPlace();
    final boolean loud = place == null || current == null || !place.getClass().equals(current.getClass());

    goTo(place, !loud);
  }

  @Override
  public void goTo(final Place place, final boolean silent) {
    previousPlace = getPlace();
    final Place futureWhere = place;

    final PlaceChangeCommand command = new PlaceChangeCommand(place, silent);

    eventBus.fireEvent(command);

    if (command.isRedirected()) {
      eventBus.fireEvent(new PlaceChangeRequestCommand(command.getRedirect()));
      command.silence();
      return;
    }

    if (!command.isCancelled()) {
      where = futureWhere;
    }

    // Even fire if silent.
    if (command.isSilent() && !command.isCancelled()) {
      Scheduler.get().scheduleDeferred(() -> {
        GWT.log("Firing command's event: " + command.getEvent().getClass().getSimpleName());
        eventBus.fireEvent(command.getEvent());
      });
    }
  }
}
