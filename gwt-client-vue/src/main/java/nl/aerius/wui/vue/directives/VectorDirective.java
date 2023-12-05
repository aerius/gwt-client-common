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
package nl.aerius.wui.vue.directives;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.axellience.vuegwt.core.annotations.directive.Directive;
import com.axellience.vuegwt.core.client.directive.VueDirective;
import com.axellience.vuegwt.core.client.vnode.VNode;
import com.axellience.vuegwt.core.client.vnode.VNodeDirective;
import com.google.gwt.resources.client.DataResource;

import elemental2.dom.Element;
import elemental2.dom.Node;

import jsinterop.base.Js;

import nl.aerius.wui.util.Base64Util;

/**
 * Example usage:
 *
 * <pre>
 * &lt;div v-mask="img.exampleIcon()" &#47;&gt;
 * </pre>
 */
@Directive
public class VectorDirective extends VueDirective {
  @Override
  public void inserted(final Element el, final VNodeDirective binding, final VNode vnode) {
    super.inserted(el, binding, vnode);

    if (binding.getValue() == null) {
      return;
    }

    final String img = ((DataResource) binding.getValue()).getSafeUri().asString();
    final String str = Base64Util.decode(img.split(";")[1].split(",")[1]);
    el.innerHTML = str;

    // Clone the data elements if any
    final List<Node> dataAttributes = Stream.of(el.getAttributeNames().asArray(new String[0]))
        .filter(v -> v.startsWith("data-"))
        .map(v -> (Node) Js.cast(el.attributes.get(v).cloneNode(true)))
        .collect(Collectors.toList());
    el.childNodes.asList().stream()
        .filter(node -> node.nodeType == (int) Node.ELEMENT_NODE)
        .forEach(node -> {
          dataAttributes.forEach(attr -> node.attributes.setNamedItem(attr));

          final Element elem = Js.uncheckedCast(node);
          elem.setAttribute("aria-hidden", true);
          elem.setAttribute("focusable", false);
        });
  }
}
