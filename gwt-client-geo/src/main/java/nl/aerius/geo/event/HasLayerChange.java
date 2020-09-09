package nl.aerius.geo.event;

import nl.aerius.geo.event.LayerChangeEvent.CHANGE;

public interface HasLayerChange {
  CHANGE getChange();
}
