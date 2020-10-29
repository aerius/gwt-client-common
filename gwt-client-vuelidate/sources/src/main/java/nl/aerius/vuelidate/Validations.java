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

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

import elemental2.core.JsObject;

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
  @JsProperty public boolean alphaNum;
  @JsProperty public boolean and;
  @JsProperty public boolean between;

  @JsProperty public boolean email;
  @JsProperty public boolean decimal;
  @JsProperty public boolean helpers;
  @JsProperty public boolean integer;
  @JsProperty public boolean ipAddress;
  @JsProperty public boolean macAddress;
  @JsProperty public boolean maxLength;
  @JsProperty public boolean maxValue;
  @JsProperty public boolean minLength;
  @JsProperty public boolean minValue;
  @JsProperty public boolean not;
  @JsProperty public boolean numeric;
  @JsProperty public boolean or;

  @JsProperty public boolean required;
  @JsProperty public boolean requiredIf;
  @JsProperty public boolean requiredUnless;
  @JsProperty public boolean sameAs;
  @JsProperty public boolean url;
}
