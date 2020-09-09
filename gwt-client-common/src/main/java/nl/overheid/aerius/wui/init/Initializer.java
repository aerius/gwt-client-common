package nl.overheid.aerius.wui.init;

import java.util.function.Consumer;

import com.google.inject.ImplementedBy;

@ImplementedBy(InitializerDummy.class)
public interface Initializer {
  default void init(final Runnable complete, final Consumer<Throwable> fail) {
    complete.run();
  }
}
