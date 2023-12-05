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

import java.util.Map;

import com.axellience.vuegwt.core.annotations.directive.Directive;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.directive.VueDirective;
import com.axellience.vuegwt.core.client.vnode.VNode;
import com.axellience.vuegwt.core.client.vnode.VNodeDirective;

import elemental2.core.JsArray;
import elemental2.core.JsObject;
import elemental2.dom.Element;

import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@Directive
public class ValidateDirective extends VueDirective {
  @Override
  public void inserted(final Element el, final VNodeDirective binding, final VNode vnode) {
    final IsVueComponent context = vnode.getContext();

    final Map<String, String> mappings = Js.cast(context.vue().$options().get("mappings"));
    final String runtimeProperty = mappings.get(binding.getArg());
    final JsPropertyMap<Object> jsContext = Js.cast(context);

    @SuppressWarnings("unchecked")
    final Validations vals = Js.uncheckedCast(((JsPropertyMap<Object>) jsContext.get("$v")).get(runtimeProperty));
    final JsArray<String> modifiers = JsObject.getOwnPropertyNames(binding.getModifiers());

    // Touch the validation on blur (to update it)
    // This circumvents binding to the validation model
    el.addEventListener(modifiers.length > 0 ? modifiers.getAt(0) : "blur", e -> {
      vals.$touch();
    });
  }
}
