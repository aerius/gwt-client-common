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
package nl.aerius.wui.init;

import java.util.function.Consumer;

import nl.aerius.wui.dev.GWTProd;
import nl.aerius.wui.util.GWTAtomicInteger;

public abstract class AbstractInitializer implements Initializer {
  private final GWTAtomicInteger counter = new GWTAtomicInteger();

  private Runnable complete;

  private final int completionThreshold;

  private Consumer<Throwable> fail;

  public AbstractInitializer(final int completionThreshold) {
    this.completionThreshold = completionThreshold;
  }

  @Override
  public void init(final Runnable complete, final Consumer<Throwable> fail) {
    this.complete = complete;
    this.fail = fail;
    init();
  }

  public abstract void init();

  protected void complete() {
    if (counter.incrementAndGet() == completionThreshold) {
      complete.run();
    }
  }

  protected void completeWithWarning(final String message, final Throwable e) {
    complete();

    GWTProd.warn("Failure while initialising application: " + message);
  }

  protected void fail(final String message, final Throwable e) {
    GWTProd.error("Unrecoverable failure while initialising application: " + message + " > " + e.getMessage());

    fail.accept(new Exception(message));
  }
}
