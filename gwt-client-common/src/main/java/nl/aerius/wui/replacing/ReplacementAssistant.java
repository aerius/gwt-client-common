package nl.aerius.wui.replacing;

import nl.aerius.wui.event.HasEventBus;

public interface ReplacementAssistant extends HasEventBus {
  String replace(String origin);

  /**
   * Replace the given string, and throw an error when there are tags that cannot
   * be replaced.
   */
  String replaceStrict(String origin);
}
