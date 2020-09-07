package nl.overheid.aerius.wui.activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class AbstractActivity<P, V extends View<P>> implements Activity<P, AcceptsOneWidget> {
  protected final V view;

  public AbstractActivity(final V view) {
    this.view = view;

    view.setPresenter(getPresenter());
  }

  @Override
  public void onStop() {}

  @Override
  public String mayStop() {
    return null;
  }

  @Override
  public void onStart() {}

  @Override
  public void onStart(final AcceptsOneWidget panel) {
    if (view instanceof IsWidget) {
      panel.setWidget((IsWidget) view);
    }

    onStart();
  }

  @Override
  public abstract P getPresenter();
}
