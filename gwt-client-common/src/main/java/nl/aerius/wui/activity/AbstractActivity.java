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
package nl.aerius.wui.activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class AbstractActivity<P, V extends View<P>> implements Activity<P, AcceptsOneWidget> {
  protected final V view;

  public AbstractActivity(final V view) {
    this.view = view;

    view.setPresenter(getPresenter());
  }

  @Override
  public void onStop() {}

  @Override
  public String mayStop() {
    return null;
  }

  @Override
  public void onStart() {}

  @Override
  public void onStart(final AcceptsOneWidget panel) {
    if (view instanceof IsWidget) {
      panel.setWidget((IsWidget) view);
    }

    onStart();
  }

  @Override
  public abstract P getPresenter();
}
