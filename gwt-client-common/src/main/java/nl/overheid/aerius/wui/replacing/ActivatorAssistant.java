package nl.overheid.aerius.wui.replacing;

import java.util.List;
import java.util.function.Consumer;

import nl.overheid.aerius.wui.event.HasEventBus;

public interface ActivatorAssistant extends HasEventBus {
  <T extends IsActivatorInfo> ReplacementRegistration register(List<T> activators, Consumer<List<T>> consumer);
}
