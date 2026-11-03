/*
 * Copyright (c) Contributors to the project
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
  private static final int UNKNOWN_INDEX = -1;

  private final EventBus eventBus;

  /**
   * Position of the current entry, kept in each entry's history state.
   */
  private int index;
  private int lastStep;
  private int revertIndex = UNKNOWN_INDEX;

  @Inject
  public HTML5Historian(final EventBus eventBus) {
    this.eventBus = eventBus;
    index = Math.max(readIndex(), 0);
    replaceState(index, Window.Location.getHref());
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

    index++;
    pushState(index, WebUtil.getAbsoluteRoot() + token);

    if (issueEvent) {
      ValueChangeEvent.fire(this, getToken());
    }
  }

  /**
   * Replaces the current entry instead of adding one.
   */
  public void replaceItem(final String token) {
    if (getToken().equals(token)) {
      return;
    }

    replaceState(index, WebUtil.getAbsoluteRoot() + token);
  }

  /**
   * Moves the browser back to the entry it left on the last Back or Forward.
   */
  public void revert() {
    if (lastStep == 0) {
      return;
    }

    revertIndex = index - lastStep;
    go(-lastStep);
  }

  @Override
  public void fireEvent(final GwtEvent<?> event) {
    eventBus.fireEvent(event);
  }

  private native void initEvent() /*-{
                                  var that = this;
                                  var oldHandler = $wnd.onpopstate;
                                  $wnd.onpopstate = $entry(function(e) {
                                  var index = typeof e.state === 'number' ? e.state : @nl.aerius.wui.history.HTML5Historian::UNKNOWN_INDEX;
                                  that.@nl.aerius.wui.history.HTML5Historian::onPopState(I)(index);
                                  if (oldHandler) {
                                  oldHandler();
                                  }
                                  });
                                  }-*/;

  private void onPopState(final int newIndex) {
    final boolean known = newIndex != UNKNOWN_INDEX;
    final boolean reverted = known && newIndex == revertIndex;
    lastStep = known ? newIndex - index : 0;
    index = known ? newIndex : index;
    revertIndex = UNKNOWN_INDEX;

    if (!reverted) {
      ValueChangeEvent.fire(this, getToken());
    }
  }

  private native int readIndex() /*-{
                                 var state = $wnd.history.state;
                                 return typeof state === 'number' ? state : @nl.aerius.wui.history.HTML5Historian::UNKNOWN_INDEX;
                                 }-*/;

  private native void pushState(int index, String url) /*-{
                                                       $wnd.history.pushState(index, $doc.title, url);
                                                       }-*/;

  private native void replaceState(int index, String url) /*-{
                                                          $wnd.history.replaceState(index, $doc.title, url);
                                                          }-*/;

  private native void go(int step) /*-{
                                   $wnd.history.go(step);
                                   }-*/;
}
