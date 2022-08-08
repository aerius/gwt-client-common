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
package nl.aerius.geo.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProxiedCallback<T> implements AsyncCallback<T> {
  private final AsyncCallback<T> parent;

  private final Runnable run;

  public ProxiedCallback(final AsyncCallback<T> parent, final Runnable run) {
    this.parent = parent;
    this.run = run;
  }

  public static <T> AsyncCallback<T> wrap(final AsyncCallback<T> parent, final Runnable run) {
    return new ProxiedCallback<T>(parent, run);
  }

  @Override
  public void onSuccess(final T result) {
    parent.onSuccess(result);
    run.run();
  }

  @Override
  public void onFailure(final Throwable caught) {
    parent.onFailure(caught);
    run.run();
  }
}
