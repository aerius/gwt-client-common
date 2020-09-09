package nl.aerius.wui.event;

import com.google.web.bindery.event.shared.EventBus;

public interface HasEventBus {
  void setEventBus(EventBus eventBus);
}
