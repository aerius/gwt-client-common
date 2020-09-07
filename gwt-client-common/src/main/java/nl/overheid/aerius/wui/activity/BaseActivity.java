package nl.overheid.aerius.wui.activity;

public interface BaseActivity<C> {
  void onStart();
  
  void onStart(C container);
}
