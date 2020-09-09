package nl.aerius.wui.replacing;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;

@Singleton
public class SimpleObservingReplacementAssistant implements ObservingReplacementAssistant {
  interface ObservingReplacementAssistantEventBinder extends EventBinder<SimpleObservingReplacementAssistant> {}

  private final ObservingReplacementAssistantEventBinder EVENT_BINDER = GWT.create(ObservingReplacementAssistantEventBinder.class);

  private class ReplacementItem {
    private final String template;
    private final Consumer<String> consumer;
    private final boolean strict;

    public ReplacementItem(final String template, final Consumer<String> consumer, final boolean strict) {
      this.template = template;
      this.consumer = consumer;
      this.strict = strict;
    }

    @Override
    public String toString() {
      return "ReplacementItem [template=" + template + ", strict=" + strict + "]";
    }
  }

  private final Map<Object, ReplacementItem> targets = new HashMap<>();
  private final Map<Object, String> cache = new HashMap<>();

  private final ReplacementAssistant replacer;

  private boolean scheduled;

  @Inject
  public SimpleObservingReplacementAssistant(final ReplacementAssistant replacer, final EventBus eventBus) {
    this.replacer = replacer;

    setEventBus(eventBus);
  }

  protected void setEventBus(final EventBus eventBus) {
    EVENT_BINDER.bindEventHandlers(this, eventBus);
  }

  @Override
  public ReplacementRegistration registerStrict(final String template, final Consumer<String> consumer) {
    return register(template, consumer, true);
  }

  @Override
  public ReplacementRegistration register(final String template, final Consumer<String> consumer) {
    return register(template, consumer, false);
  }

  private ReplacementRegistration register(final String template, final Consumer<String> consumer, final boolean strict) {
    final Object handle = new Object();

    // Add tracker
    final ReplacementItem item = new ReplacementItem(template, consumer, strict);
    targets.put(handle, item);

    // Add initial replacement to cache
    acceptReplacement(item, replace(item));

    // Return a replacement registration that the caller can use to remove the
    // tracking
    return () -> targets.remove(handle);
  }

  protected void scheduleUpdate() {
    if (scheduled) {
      return;
    }

    scheduled = true;
    Scheduler.get().scheduleDeferred(() -> {
      updateNow();
      scheduled = false;
    });
  }

  private void updateNow() {
    for (final ReplacementItem item : targets.values()) {
      acceptReplacementCached(item, replace(item));
    }
  }

  private Optional<String> replace(final ReplacementItem item) {
    try {
      final String replaced = item.strict ? replacer.replaceStrict(item.template) : replacer.replace(item.template);

      return Optional.of(replaced);
    } catch (final IllegalStateException e) {
      return Optional.empty();
    }
  }

  private void acceptReplacementCached(final ReplacementItem item, final Optional<String> replacement) {
    if (cache.containsKey(item.consumer) && cache.get(item.consumer).equals(replacement.orElse(null))) {
      return;
    }

    acceptReplacement(item, replacement);
  }

  private void acceptReplacement(final ReplacementItem item, final Optional<String> replacement) {
    item.consumer.accept(replacement.orElse(null));
    if (replacement.isPresent()) {
      cache.put(item.consumer, replacement.get());
    } else {
      cache.remove(item.consumer);
    }
  }
}
