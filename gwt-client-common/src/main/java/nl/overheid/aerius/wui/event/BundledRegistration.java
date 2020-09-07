package nl.overheid.aerius.wui.event;

import java.util.ArrayList;
import java.util.List;

import com.google.web.bindery.event.shared.HandlerRegistration;

public class BundledRegistration {
  private final List<Runnable> registrations = new ArrayList<>();

  public void add(final HandlerRegistration registration) {
    add((Runnable) () -> registration.removeHandler());
  }

  public void add(final Runnable runnable) {
    registrations.add(runnable);
  }

  public void retire() {
    registrations.forEach(Runnable::run);
    registrations.clear();
  }
}
