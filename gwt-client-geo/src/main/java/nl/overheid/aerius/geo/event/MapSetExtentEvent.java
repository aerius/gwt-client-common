package nl.overheid.aerius.geo.event;

import nl.overheid.aerius.wui.event.SimpleGenericEvent;

public class MapSetExtentEvent extends SimpleGenericEvent<String> {
  public MapSetExtentEvent(final String extent) {
    super(extent);
  }
}
