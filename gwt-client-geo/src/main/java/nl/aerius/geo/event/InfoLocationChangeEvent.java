package nl.aerius.geo.event;


import nl.aerius.geo.domain.ReceptorPoint;
import nl.aerius.wui.event.SimpleGenericEvent;

public class InfoLocationChangeEvent extends SimpleGenericEvent<ReceptorPoint> implements Unscoped {
  public InfoLocationChangeEvent() {}

  public InfoLocationChangeEvent(final ReceptorPoint value) {
    super(value);
  }
}
