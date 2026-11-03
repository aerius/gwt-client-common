/*
 * Copyright (c) Contributors to the project
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
package nl.aerius.wui.history;

import com.google.gwt.core.shared.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import nl.aerius.wui.event.NotifyHistoryEvent;
import nl.aerius.wui.event.PlaceChangeEvent;
import nl.aerius.wui.place.ApplicationPlace;
import nl.aerius.wui.place.DefaultPlace;
import nl.aerius.wui.place.Place;
import nl.aerius.wui.place.PlaceController;
import nl.aerius.wui.place.PlaceResolver;
import nl.aerius.wui.place.TokenizedPlace;

@Singleton
public class HistoryManagerImpl implements HistoryManager {
  interface HistoryManagerImplEventBinder extends EventBinder<HistoryManagerImpl> {}

  private final HistoryManagerImplEventBinder EVENT_BINDER = GWT.create(HistoryManagerImplEventBinder.class);

  private final ApplicationPlace defaultPlace;

  private final PlaceController placeController;

  private final PlaceResolver resolver;

  private final EventBus eventBus;

  private final HTML5Historian historian;

  private final PlaceHistoryMapper mapper;

  @Inject
  public HistoryManagerImpl(@DefaultPlace final ApplicationPlace defaultPlace, final PlaceController placeController, final PlaceResolver resolver,
      final EventBus eventBus, final HTML5Historian historian, final PlaceHistoryMapper mapper) {
    this.defaultPlace = defaultPlace;
    this.placeController = placeController;
    this.resolver = resolver;
    this.historian = historian;
    this.mapper = mapper;
    this.eventBus = eventBus;

    EVENT_BINDER.bindEventHandlers(this, eventBus);

    historian.addValueChangeHandler(event -> followBrowser(event.getValue()));
  }

  /**
   * The app moved, so the browser follows. Adds nothing if the browser is already on this place.
   */
  @EventHandler
  public void onPlaceChangeEvent(final PlaceChangeEvent e) {
    final Place value = e.getValue();

    if (value instanceof TokenizedPlace) {
      historian.newItem(mapper.getToken((TokenizedPlace) value), false);
    }
  }

  @Override
  public void handleCurrentHistory() {
    followBrowser(historian.getToken());
  }

  @Override
  public TokenizedPlace getCurrentPlace() {
    final String token = historian.getToken();

    return token == null || token.isEmpty() ? null : mapper.getPlace(token);
  }

  @Override
  public TokenizedPlace getPlace(final String token) {
    return mapper.getPlace(token);
  }

  /**
   * The browser moved, so the app follows. The entry is corrected first, so the place change adds nothing to the history.
   */
  private void followBrowser(final String token) {
    final TokenizedPlace requested = placeFor(token);
    final Place place = resolver.resolve(requested);
    final Place before = placeController.getPlace();

    if (place instanceof TokenizedPlace) {
      historian.replaceItem(mapper.getToken((TokenizedPlace) place));
    }

    placeController.goTo(place, false);

    if (isCancelled(place, before)) {
      historian.revert();
    }

    eventBus.fireEvent(new NotifyHistoryEvent(requested));
  }

  /**
   * Compares place objects on purpose: a cancelled change leaves the controller on the same object, and asking for the current object is no change.
   */
  private boolean isCancelled(final Place place, final Place before) {
    return place != before && placeController.getPlace() == before;
  }

  private TokenizedPlace placeFor(final String token) {
    final TokenizedPlace place = token == null || token.isEmpty() ? null : getPlace(token);

    return place == null ? defaultPlace : place;
  }
}
