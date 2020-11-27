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
package nl.aerius.wui.place;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import nl.aerius.wui.command.PlaceChangeCommand;
import nl.aerius.wui.command.PlaceChangeRequestCommand;

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
