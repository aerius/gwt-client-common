/*
 * Copyright Dutch Ministry of Agriculture, Nature and Food Quality
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
package nl.overheid.aerius.wui.history;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler.Historian;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import nl.overheid.aerius.wui.event.NotifyHistoryEvent;
import nl.overheid.aerius.wui.event.PlaceChangeEvent;
import nl.overheid.aerius.wui.place.ApplicationPlace;
import nl.overheid.aerius.wui.place.DefaultPlace;
import nl.overheid.aerius.wui.place.Place;
import nl.overheid.aerius.wui.place.PlaceController;
import nl.overheid.aerius.wui.place.TokenizedPlace;

public class HistoryManagerImpl implements HistoryManager {
  interface HistoryManagerImplEventBinder extends EventBinder<HistoryManagerImpl> {}

  private final HistoryManagerImplEventBinder EVENT_BINDER = GWT.create(HistoryManagerImplEventBinder.class);

  private final ApplicationPlace defaultPlace;

  private final PlaceController placeController;

  private final EventBus eventBus;

  private final Historian historian;

  private final PlaceHistoryMapper mapper;

  @Inject
  public HistoryManagerImpl(@DefaultPlace final ApplicationPlace defaultPlace, final PlaceController placeController, final EventBus eventBus,
      final Historian historian, final PlaceHistoryMapper mapper) {
    this.defaultPlace = defaultPlace;
    this.placeController = placeController;
    this.historian = historian;
    this.mapper = mapper;
    this.eventBus = eventBus;

    EVENT_BINDER.bindEventHandlers(this, eventBus);

    historian.addValueChangeHandler(event -> {
      handleHistoryToken(event.getValue(), false);
    });
  }

  @EventHandler
  public void onPlaceChangeEvent(final PlaceChangeEvent e) {
    final Place value = e.getValue();

    if (value instanceof TokenizedPlace) {
      historian.newItem(mapper.getToken((TokenizedPlace) value), false);
    }
  }

  @Override
  public void handleCurrentHistory() {
    handleHistoryToken(historian.getToken(), false);
  }

  @Override
  public TokenizedPlace getPlace(final String token) {
    return mapper.getPlace(token);
  }

  private void handleHistoryToken(final String token, final boolean silent) {
    TokenizedPlace newPlace = null;

    if (token == null || token.isEmpty()) {
      newPlace = defaultPlace;
    }

    if (newPlace == null) {
      newPlace = getPlace(token);
    }

    if (newPlace == null) {
      newPlace = defaultPlace;
    }

    placeController.goTo(newPlace, silent);
    eventBus.fireEvent(new NotifyHistoryEvent(newPlace));
  }
}
