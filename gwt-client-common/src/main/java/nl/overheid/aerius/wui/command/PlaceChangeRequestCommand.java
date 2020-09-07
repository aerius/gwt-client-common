package nl.overheid.aerius.wui.command;

import nl.overheid.aerius.wui.event.SimpleGenericEvent;
import nl.overheid.aerius.wui.place.Place;

public class PlaceChangeRequestCommand extends SimpleGenericEvent<Place> {
  public PlaceChangeRequestCommand(final Place obj) {
    super(obj);
  }
}
