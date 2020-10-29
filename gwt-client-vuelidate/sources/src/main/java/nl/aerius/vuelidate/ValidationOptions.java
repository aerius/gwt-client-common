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

import java.util.HashMap;
import java.util.Map;

import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.options.CustomizeOptions;
import com.axellience.vuegwt.core.client.component.options.VueComponentOptions;
import com.axellience.vuegwt.core.client.tools.VueGWTTools;

import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

public abstract class ValidationOptions<T extends IsVueComponent> implements CustomizeOptions {

  public static interface ValidatorInstaller {
    /**
     * <p>
     * Install a validator under the given field name, marked by the given field
     * marker.
     * </p>
     *
     * <p>
     * Example usage:
     * </p>
     *
     * <pre>
     * install("name", () -> instance.name = null, ValidatorBuilder.create().required());
     * </pre>
     *
     * @param key       The key, in plain text, that this field should be available
     *                  under in the template (usually the same as the field name)
     * @param marker    A marker runnable, used to figure out what the runtime name
     *                  for the validation field is. This runnable should set the
     *                  instance field to null (or 0 or false, depending on type)
     * @param validator The validator object to validate against.
     */
    void install(String key, Runnable marker, Object validator);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public void customizeOptions(final VueComponentOptions options) {
    options.addMixin(Vuelidate.validationMixin);

    final T instance = Js.cast(options);
    final JsPropertyMap<Object> validations = new JsPropertyMap<Object>() {};
    final Map<String, String> mappings = new HashMap<>();
    
    constructValidators(instance, (name, marker, validator) -> {
      log("Installing validator: " + name);
      final String fieldName = VueGWTTools.getFieldName(instance, marker);
      validations.set(fieldName, validator);
      mappings.put(name, fieldName);
    });

    options.set("validations", validations);
    options.set("mappings", mappings);
  }

  public static native void log(Object message) /*-{
    console.info(message);
  }-*/;

  /**
   * <p>
   * Construct the validators for the given instance. Cast the instance to the
   * correctly typed component instance like so:
   * </p>
   *
   * <pre>
   * final ExampleComponent instance = Js.uncheckedCast(parent);
   * </pre>
   *
   * <p>
   * Use the {@link installer} to install one or multiple validators for the
   * component's data fields
   * </p>
   *
   * @param instance
   * @param installer
   */
  protected abstract void constructValidators(final Object instance, final ValidatorInstaller installer);
}
