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
package nl.aerius.wui.activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.ResettableEventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import nl.aerius.wui.command.HasCommandRouter;
import nl.aerius.wui.command.PlaceChangeCommand;
import nl.aerius.wui.dev.GWTProd;
import nl.aerius.wui.event.HasEventBus;
import nl.aerius.wui.place.Place;
import nl.aerius.wui.place.PlaceController;

public abstract class AbstractActivityManager<C> implements ActivityManager<C> {
  private final ActivityManagerImplEventBinder EVENT_BINDER = GWT.create(ActivityManagerImplEventBinder.class);

  @SuppressWarnings("rawtypes")
  interface ActivityManagerImplEventBinder extends EventBinder<AbstractActivityManager> {}

  private final ActivityMapper<C> mapper;
  private final PlaceController placeController;

  private C panel;

  private final ResettableEventBus activityEventBus;

  private Activity<?, ?> currentActivity;

  public AbstractActivityManager(final EventBus globalEventBus, final PlaceController placeController, final ActivityMapper<C> mapper) {
    this.placeController = placeController;
    this.mapper = mapper;

    activityEventBus = new ResettableEventBus(globalEventBus);

    EVENT_BINDER.bindEventHandlers(this, globalEventBus);
  }

  @EventHandler
  public void onPlaceChangeCommand(final PlaceChangeCommand c) {
    final Place previousPlace = placeController.getPreviousPlace();
    final Place place = c.getValue();

    if (previousPlace != null && previousPlace.getClass().equals(place.getClass())) {
      return;
    }

    final boolean delegateSuccess = delegateToActivity(currentActivity, activityEventBus, c);
    if (delegateSuccess) {
      GWTProd.log("ActivityManager", "Delegated to current activity.");
      return;
    }

    if (c.isRedirected()) {
      GWTProd.log("ActivityManager", "Cancelling because place is redirected. (to " + c.getRedirect() + ")");
      c.silence();
      c.cancel();
      return;
    }

    // Suspend previous activity
    final boolean suspendSuccess = suspendActivity(currentActivity);
    if (!suspendSuccess) {
      c.silence();
      c.cancel();
      return;
    }

    // Remove event handlers
    activityEventBus.removeHandlers();

    // Start next activity
    final Activity<?, C> activity = mapper.getActivity(place);
    if (activity instanceof HasEventBus) {
      ((HasEventBus) activity).setEventBus(activityEventBus);
    }

    currentActivity = activity;

    GWTProd.log("ActivityManager", "Starting activity: " + currentActivity.getClass().getSimpleName());

    // Start and delegate
    activity.onStart(panel);
    if (activity instanceof HasCommandRouter) {
      ((HasCommandRouter) activity).onStart();
    }
    if (activity instanceof DelegableActivity) {
      final DelegableActivity act = (DelegableActivity) activity;

      if (act.isDelegable(place)) {
        act.delegate(activityEventBus, c);
      }
    }
  }

  private boolean delegateToActivity(final Activity<?, ?> activity, final ResettableEventBus eventBus, final PlaceChangeCommand c) {
    if (activity instanceof DelegableActivity) {
      final DelegableActivity act = (DelegableActivity) activity;

      if (act.isDelegable(c.getValue())) {
        return act.delegate(eventBus, c);
      } else {
        return false;
      }
    }

    return false;
  }

  @Override
  public void setPanel(final C panel) {
    this.panel = panel;
  }

  private static <C> boolean suspendActivity(final Activity<?, C> currentActivity) {
    if (currentActivity == null) {
      return true;
    }

    final String stop = currentActivity.mayStop();
    if (stop != null) {
      final boolean confirm = Window.confirm(stop);

      if (confirm) {
        currentActivity.onStop();
      } else {
        return false;
      }
    } else {
      currentActivity.onStop();
    }

    return true;
  }
}
