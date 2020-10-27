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
package nl.overheid.aerius.application;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Computed;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasCreated;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;

import nl.overheid.aerius.vuelidate.ValidateDirective;
import nl.overheid.aerius.vuelidate.util.VuelidateUtil;

@Component(
    customizeOptions = DemoValidators.class,
    directives = ValidateDirective.class)
public class DemoComponent implements IsVueComponent, HasCreated {
  @Data String name;

  @Data String email;
  @Data String secondaryEmail = "";

  @Data boolean submitted;

  @Data boolean secondaryEmailRequired = true;

  @JsProperty(name = "$v") DemoValidations validation;

  @Computed
  public DemoValidations getV() {
    return validation;
  }

  @JsMethod
  public boolean isSecondaryEmailValid() {
    return !secondaryEmailRequired || !secondaryEmail.isEmpty();
  }

  @Override
  public void created() {
    VuelidateUtil.proxy(this);
  }

  @JsMethod
  public void submit() {
    if (getV().isInvalid() || !isSecondaryEmailValid()) {
      submitted = false;
      return;
    }

    // Post
    submitted = true;
  }
}
