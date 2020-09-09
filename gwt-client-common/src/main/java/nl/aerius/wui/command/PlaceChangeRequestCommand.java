package nl.aerius.wui.command;

import nl.aerius.wui.event.SimpleGenericEvent;
import nl.aerius.wui.place.Place;

public class PlaceChangeRequestCommand extends SimpleGenericEvent<Place> {
  public PlaceChangeRequestCommand(final Place obj) {
    super(obj);
  }
}
