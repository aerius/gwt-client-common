package nl.overheid.aerius.wui.event;

import nl.overheid.aerius.wui.place.TokenizedPlace;

public class NotifyHistoryEvent extends SimpleGenericEvent<TokenizedPlace> {
  public NotifyHistoryEvent(final TokenizedPlace value) {
    super(value);
  }
}
