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
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "validators")
public class DefaultValidators {
  public static JsObject alpha;
  public static JsObject alphaNum;
  public static JsObject and;
  public static JsObject between;
  public static JsObject email;
  public static JsObject decimal;
  public static JsObject helpers;
  public static JsObject integer;
  public static JsObject ipAddress;
  public static JsObject macAddress;
  public static JsObject maxLength;

  @JsOverlay
  public static JsObject maxValue() {
    return maxValue(Integer.MAX_VALUE);
  }

  public static native JsObject maxValue(int num);

  public static native JsObject maxValue(double num);

  public static JsObject minLength;

  @JsOverlay
  public static JsObject minValue() {
    return minValue(Integer.MIN_VALUE);
  }

  public static native JsObject minValue(int num);

  public static native JsObject minValue(double num);

  public static JsObject not;
  public static JsObject numeric;
  public static JsObject or;
  public static JsObject required;
  public static JsObject requiredIf;
  public static JsObject requiredUnless;
  public static JsObject sameAs;
  public static JsObject url;
}
