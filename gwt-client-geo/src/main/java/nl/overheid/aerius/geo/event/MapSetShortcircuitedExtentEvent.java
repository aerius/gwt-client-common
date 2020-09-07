package nl.overheid.aerius.geo.event;

import nl.overheid.aerius.wui.event.SimpleGenericEvent;

public class MapSetShortcircuitedExtentEvent extends SimpleGenericEvent<String> {
  public MapSetShortcircuitedExtentEvent(final String extent) {
    super(extent);
  }
}
