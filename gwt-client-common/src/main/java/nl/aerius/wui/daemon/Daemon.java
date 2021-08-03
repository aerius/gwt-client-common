package nl.aerius.wui.daemon;

import nl.aerius.wui.event.HasEventBus;

public interface Daemon extends HasEventBus {
  default void init() {}
}
