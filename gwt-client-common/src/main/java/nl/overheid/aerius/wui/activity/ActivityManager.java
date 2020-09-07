package nl.overheid.aerius.wui.activity;

import com.google.inject.ImplementedBy;

@ImplementedBy(WidgetActivityManager.class)
public interface ActivityManager<C> {
  void setPanel(C panel);
}
