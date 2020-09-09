package nl.aerius.geo.domain;

import nl.aerius.geo.event.MapEventBus;
import nl.aerius.geo.wui.Map;

public interface IsMapCohort {
  void notifyMap(Map map, MapEventBus mapEventBus);
}
