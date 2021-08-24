package nl.aerius.wui.command;

import com.google.web.bindery.event.shared.Event;

import nl.aerius.wui.event.CommandEventBus;
import nl.aerius.wui.util.ExceptionHelper;

/**
 * A CommandEventBus that does its own error handling.
 */
public class GuardedEventBus extends CommandEventBus {
  @Override
  protected void tryInnerFire(final boolean enforce, final Event<?> event) {
    try {
      super.tryInnerFire(enforce, event);
    } catch (final Exception e) {
      ExceptionHelper.reportUltimateUncaughtException(this, e);
    }
  }

  @Override
  protected void tryInnerFireFromSource(final boolean enforce, final Event<?> event, final Object source) {
    try {
      super.tryInnerFireFromSource(enforce, event, source);
    } catch (final Exception e) {
      ExceptionHelper.reportUltimateUncaughtException(this, e);
    }
  }
}
