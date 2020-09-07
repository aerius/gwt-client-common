package nl.overheid.aerius.geo.event;

import nl.overheid.aerius.geo.event.LayerChangeEvent.CHANGE;

public interface HasLayerChange {
  CHANGE getChange();
}
