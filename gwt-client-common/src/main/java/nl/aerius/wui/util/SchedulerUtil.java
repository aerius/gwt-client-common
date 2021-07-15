/*
 * Copyright the State of the Netherlands
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package nl.aerius.wui.util;

import java.util.function.Supplier;

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

  public static void repeatUntil(final Supplier<Boolean> condition, final int delay) {
    Scheduler.get().scheduleFixedDelay(() -> {
      return condition.get();
    }, delay);
  }

  public static void repeatUntil(final Runnable runner, final Supplier<Boolean> condition, final int delay) {
    Scheduler.get().scheduleFixedDelay(() -> {
      runner.run();
      return condition.get();
    }, delay);
  }
}
