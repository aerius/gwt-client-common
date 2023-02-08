package nl.aerius.vuelidate;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class CommonParams {
  @JsProperty public String type;
  @JsProperty public double max;
  @JsProperty public double min;
}
