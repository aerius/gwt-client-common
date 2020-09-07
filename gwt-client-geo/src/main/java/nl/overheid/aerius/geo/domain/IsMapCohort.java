package nl.overheid.aerius.geo.domain;

import nl.overheid.aerius.geo.event.MapEventBus;
import nl.overheid.aerius.geo.wui.Map;

public interface IsMapCohort {
  void notifyMap(Map map, MapEventBus mapEventBus);
}
