package nl.overheid.aerius.wui.activity;

public interface Activity<P, C> extends BaseActivity<C> {
  @Override
  void onStart(C panel);

  void onStop();

  String mayStop();

  P getPresenter();
}
