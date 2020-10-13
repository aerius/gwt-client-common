package nl.aerius.wui.dev;

import nl.aerius.wui.event.SimpleGenericEvent;

public class WarningEvent extends SimpleGenericEvent<String> {
  public WarningEvent(final String msg) {
    super(msg);
  }
}
