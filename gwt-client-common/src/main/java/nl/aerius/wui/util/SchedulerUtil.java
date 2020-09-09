package nl.aerius.wui.util;

import com.google.gwt.core.client.Scheduler;

public class SchedulerUtil {
  public static void delay(final Runnable runner, final int delay) {
    Scheduler.get().scheduleFixedDelay(() -> {
      runner.run();
      return false;
    }, delay);
  }

  public static void delay(final Runnable runner) {
    Scheduler.get().scheduleDeferred(runner::run);
  }
}
