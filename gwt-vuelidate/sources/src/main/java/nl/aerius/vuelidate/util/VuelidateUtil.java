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
package nl.aerius.vuelidate.util;

import java.util.Map;

import com.axellience.vuegwt.core.client.component.IsVueComponent;

import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

public final class VuelidateUtil {

  private VuelidateUtil() {
    // Util class.
  }

  /**
   * Proxy a given field with the contents of another field
   */
  public static void proxy(final IsVueComponent instance, final String field, final String runtime) {
    final JsPropertyMap<Object> options = Js.cast(instance);
    final JsPropertyMap<Object> validations = Js.cast(options.get("$v"));

    validations.set(field, Js.cast(validations.get(runtime)));
  }

  /**
   * Proxy all validation field mappings of a given component instance
   */
  public static void proxy(final IsVueComponent instance) {
    final JsPropertyMap<Object> object = Js.cast(instance.vue().$options().get("mappings"));

    final Map<String, String> mappings = Js.cast(object);
    mappings.forEach((k, v) -> proxy(instance, k, v));
  }
}
