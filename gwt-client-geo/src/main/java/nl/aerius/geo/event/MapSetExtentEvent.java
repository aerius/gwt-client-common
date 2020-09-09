package nl.aerius.geo.event;

import nl.aerius.wui.event.SimpleGenericEvent;

public class MapSetExtentEvent extends SimpleGenericEvent<String> {
  public MapSetExtentEvent(final String extent) {
    super(extent);
  }
}
