package nl.overheid.aerius.geo.event;


import nl.overheid.aerius.geo.domain.ReceptorPoint;
import nl.overheid.aerius.wui.event.SimpleGenericEvent;

public class InfoLocationChangeEvent extends SimpleGenericEvent<ReceptorPoint> implements Unscoped {
  public InfoLocationChangeEvent() {}

  public InfoLocationChangeEvent(final ReceptorPoint value) {
    super(value);
  }
}
