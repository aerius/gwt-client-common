package nl.aerius.geo.event;

import nl.aerius.wui.event.SimpleGenericEvent;

public class MapSetShortcircuitedExtentEvent extends SimpleGenericEvent<String> {
  public MapSetShortcircuitedExtentEvent(final String extent) {
    super(extent);
  }
}
