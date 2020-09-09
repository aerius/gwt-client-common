package nl.aerius.wui.activity;

import com.google.web.bindery.event.shared.EventBus;

import nl.aerius.wui.command.PlaceChangeCommand;
import nl.aerius.wui.place.Place;

public interface DelegableActivity {
  default boolean delegate(final EventBus eventBus, final PlaceChangeCommand c) {
    return false;
  }

  default boolean isDelegable(final Place place) {
    return false;
  }
}
