package nl.overheid.aerius.wui.init;

/**
 * The browser sandboxes uncaught exceptions when they are thrown following a
 * cross-origin request, and replaces them with a javascript exception that has
 * none of the information we need. This static cache hides the exception,
 * allowing the uncaught exception handler to retrieve it.
 *
 * Note this class has a single-purpose utility, hence the reason using static
 * fields here is acceptable, and should not be used in the nominal case.
 */
public class ExceptionalPirateCache {
  private static Throwable last;

  public static void hide(final Throwable last) {
    ExceptionalPirateCache.last = last;
  }

  public static Throwable pop() {
    final Throwable t = last;
    last = null;
    return t;
  }
}
