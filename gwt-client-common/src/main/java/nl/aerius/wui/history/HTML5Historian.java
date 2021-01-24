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
package nl.aerius.wui.history;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.place.shared.PlaceHistoryHandler.Historian;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

import nl.aerius.wui.util.WebUtil;

@Singleton
public class HTML5Historian implements Historian, HasValueChangeHandlers<String> {
  private final EventBus eventBus;

  @Inject
  public HTML5Historian(final EventBus eventBus) {
    this.eventBus = eventBus;
    initEvent();
  }

  @Override
  public com.google.gwt.event.shared.HandlerRegistration addValueChangeHandler(final ValueChangeHandler<String> valueChangeHandler) {
    final HandlerRegistration reg = eventBus.addHandler(ValueChangeEvent.getType(), valueChangeHandler);

    return () -> reg.removeHandler();
  }

  @Override
  public String getToken() {
    final String token = Window.Location.getPath() + Window.Location.getQueryString() + Window.Location.getHash();

    return token.substring(WebUtil.getAbsoluteRoot().length());
  }

  @Override
  public void newItem(final String token, final boolean issueEvent) {
    if (getToken().equals(token)) {
      return;
    }

    pushState(WebUtil.getAbsoluteRoot() + token);

    if (issueEvent) {
      ValueChangeEvent.fire(this, getToken());
    }
  }

  @Override
  public void fireEvent(final GwtEvent<?> event) {
    eventBus.fireEvent(event);
  }

  private native void initEvent() /*-{
                                  var that = this;
                                  var oldHandler = $wnd.onpopstate;
                                  $wnd.onpopstate = $entry(function(e) {
                                  that.@nl.aerius.wui.history.HTML5Historian::onPopState()();
                                  if (oldHandler) {
                                  oldHandler();
                                  }
                                  });
                                  }-*/;

  private void onPopState() {
    ValueChangeEvent.fire(this, getToken());
  }

  private native void pushState(String url) /*-{
                                            $wnd.history.pushState(null, $doc.title, url);
                                            }-*/;
}
