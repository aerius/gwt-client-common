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
package nl.aerius.wui.vue.activity;

import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.vue.VueComponentFactory;

import nl.aerius.wui.activity.Activity;
import nl.aerius.wui.event.BasicEventComponent;
import nl.aerius.wui.event.HasEventBus;
import nl.aerius.wui.vue.AcceptsOneComponent;

public abstract class AbstractVueActivity<P, V extends IsVueComponent, F extends VueComponentFactory<V>>
    extends BasicEventComponent implements Activity<P, AcceptsOneComponent>, HasEventBus {
  private final F factory;

  public AbstractVueActivity(final F factory) {
    this.factory = factory;
  }

  @Override
  public void onStop() {}

  @Override
  public String mayStop() {
    return null;
  }

  public void setView(final V view) {}

  @Override
  public void onStart(final AcceptsOneComponent panel) {
    if (panel instanceof HasEventBus) {
      ((HasEventBus) panel).setEventBus(eventBus);
    }
    panel.setComponent(factory, getPresenter());
    onStart();
  }

  /**
   * A convenience method that can be overridden when the panel is irrelevant.
   */
  @Override
  public void onStart() {
    // No-op
  }

  @Override
  public abstract P getPresenter();
}
