package nl.overheid.aerius.wui.activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import nl.overheid.aerius.wui.place.PlaceController;

public class WidgetActivityManager extends AbstractActivityManager<AcceptsOneWidget> {
  @Inject
  public WidgetActivityManager(final EventBus globalEventBus, final PlaceController placeController, final ActivityMapper<AcceptsOneWidget> mapper) {
    super(globalEventBus, placeController, mapper);
  }
}
