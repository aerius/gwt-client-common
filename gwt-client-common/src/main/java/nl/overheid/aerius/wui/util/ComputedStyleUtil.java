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
package nl.overheid.aerius.wui.util;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

import jsinterop.base.Js;

/**
 * Work-around util that can force a DOM element to render even if the JS event loop isn't complete yet.
 */
public final class ComputedStyleUtil {
  private ComputedStyleUtil() { }

  private static final String DUMMY_PROP = "height";

  /**
   * Get the computed style for the given property. Renders the element to get the actual/computed property.
   *
   * @param el Element to get the property for.
   * @param prop Property to get.
   * @return Post-render property value.
   */
  public static String getStyleProperty(final elemental2.dom.Element elemental, final String prop) {
    return getStyleProperty((Element) Js.cast(elemental), prop);
  }

  /**
   * Get the computed style for the given property. Renders the element to get the actual/computed property.
   *
   * @param el Element to get the property for.
   * @param prop Property to get.
   * @return Post-render property value.
   */
  public static native String getStyleProperty(Element el, String prop) /*-{
    var computedStyle;
    if (document.defaultView && document.defaultView.getComputedStyle) {
      computedStyle = document.defaultView.getComputedStyle(el, null)[prop];
    } else if (el.currentStyle) {
      computedStyle = el.currentStyle[prop];
    } else {
      computedStyle = el.style[prop];
    }
    return computedStyle;
  }-*/;

  /**
   * Force an element to render, even if the JS event loop isn't complete.
   *
   * @param el Element to render.
   */
  public static void forceStyleRender(final Element el) {
    if (el == null || el.getParentElement() == null) {
      GWT.log("Something is null, bugging out!");
      return;
    }

    getStyleProperty(el, DUMMY_PROP);
  }

  /**
   * Force an element to render, even if the JS event loop isn't complete.
   *
   * @param widg Widget to render.
   */
  public static void forceStyleRender(final Widget widg) {
    forceStyleRender(widg.getElement());
  }

  /**
   * Force an element to render, even if the JS event loop isn't complete.
   *
   * @param widg Widget to render.
   */
  public static void forceStyleRender(final elemental2.dom.Element elemental) {
    forceStyleRender((Element) Js.cast(elemental));
  }
}