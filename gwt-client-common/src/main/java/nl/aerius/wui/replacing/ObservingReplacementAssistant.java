package nl.aerius.wui.replacing;

import java.util.function.Consumer;

public interface ObservingReplacementAssistant {
  ReplacementRegistration register(String template, Consumer<String> onChange);

  ReplacementRegistration registerStrict(String template, Consumer<String> onChange);
}
