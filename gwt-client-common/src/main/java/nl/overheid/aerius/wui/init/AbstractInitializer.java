package nl.overheid.aerius.wui.init;

import java.util.function.Consumer;

import nl.overheid.aerius.wui.dev.GWTProd;
import nl.overheid.aerius.wui.util.GWTAtomicInteger;

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
