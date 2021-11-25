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
package nl.aerius.wui.vue.transition;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Prop;
import com.axellience.vuegwt.core.client.Vue;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasCreated;

import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

import jsinterop.annotations.JsMethod;

import nl.aerius.wui.util.ComputedStyleUtil;

@Component
public class HorizontalCollapseGroup implements IsVueComponent, HasCreated {
  @Prop String target;

  @Prop String tag;
  @Data String tagData = "div";

  @JsMethod
  public void enter(final Element el) {
    setWidth(el, ((HTMLElement) el).scrollWidth);
  }

  @Override
  public void created() {
    if (tag != null) {
      this.tagData = this.tag;
    }
  }

  @JsMethod
  public void afterEnter(final Element el) {
    clearWidth(el);
  }

  @JsMethod
  public void leave(final Element el) {
    setWidth(el, ((HTMLElement) el).scrollWidth);
    ComputedStyleUtil.forceStyleRender(el);
    Vue.nextTick(() -> setWidth(el, 0));
  }

  @JsMethod
  public void afterLeave(final Element el) {
    clearWidth(el);
  }

  private void setWidth(final Element el, final int width) {
    if (width > 0) {
      ((HTMLElement) el).style.set("width", (target == null ? String.valueOf(width) : target) + "px");
    } else {
      ((HTMLElement) el).style.set("width", String.valueOf(width) + "px");
    }
  }

  private void clearWidth(final Element el) {
    ((HTMLElement) el).style.removeProperty("width");
  }
}
