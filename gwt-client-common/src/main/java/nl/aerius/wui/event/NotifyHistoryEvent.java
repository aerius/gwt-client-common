package nl.aerius.wui.event;

import nl.aerius.wui.place.TokenizedPlace;

public class NotifyHistoryEvent extends SimpleGenericEvent<TokenizedPlace> {
  public NotifyHistoryEvent(final TokenizedPlace value) {
    super(value);
  }
}
