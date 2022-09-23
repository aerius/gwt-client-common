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
package nl.aerius.wui.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class ForwardedAsyncCallback<F, C> implements AsyncCallback<C> {
  protected AsyncCallback<F> callback;

  public ForwardedAsyncCallback(final AsyncCallback<F> callback) {
    this.callback = callback;
  }

  @Override
  public void onFailure(final Throwable caught) {
    callback.onFailure(caught);
  }

  @Override
  public void onSuccess(final C result) {
    F res;

    try {
      res = convert(result);
    } catch (final Exception e) {
      onFailure(e);
      return;
    }

    callback.onSuccess(res);
  }

  public abstract F convert(C content);
}
