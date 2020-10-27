/*
 * Copyright the State of the Netherlands
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package nl.aerius.vuelidate;

import elemental2.core.JsObject;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Validations implements JsPropertyMap<Object> {
  @JsProperty(name = "$invalid") public boolean invalid;
  @JsProperty(name = "$dirty") public boolean dirty;
  @JsProperty(name = "$anyDirty") public boolean anyDirty;

  @JsProperty(name = "$model") public String model;

  @JsProperty(name = "$error") public boolean error;
  @JsProperty(name = "$anyError") public boolean anyError;
  @JsProperty(name = "$pending") public boolean pending;

  @JsProperty(name = "$params") public JsObject params;
  @JsProperty(name = "$each") public JsObject each;
  @JsProperty(name = "$iter") public JsObject iter;

  public native void $touch();

  public native void $reset();

  public native void $flattenParams();

  @JsOverlay
  public final boolean isInvalid() {
    return invalid;
  }

  @JsOverlay
  public final boolean isValid(final String key) {
    return v(key);
  }

  @JsOverlay
  public final boolean v(final String key) {
    return (boolean) JsPropertyMap.super.get(key);
  }

  @JsOverlay
  public final Validations input(final String key) {
    return Js.uncheckedCast(get(key));
  }

  /**
   * Default validators, custom ones should use v("key") or add it manually.
   */

  @JsProperty public boolean alpha;
  public static boolean alphaNum;
  public static boolean and;
  public static boolean between;

  @JsProperty public boolean email;
  public static boolean decimal;
  public static boolean helpers;
  public static boolean integer;
  public static boolean ipAddress;
  public static boolean macAddress;
  public static boolean maxLength;
  public static boolean maxValue;
  public static boolean minLength;
  public static boolean minValue;
  public static boolean not;
  public static boolean numeric;
  public static boolean or;

  @JsProperty public boolean required;
  public static boolean requiredIf;
  public static boolean requiredUnless;
  public static boolean sameAs;
  public static boolean url;
}
