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
package nl.aerius.wui.future;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class JsonParserCallback<T> implements AsyncCallback<JSONValue> {
  private final AsyncCallback<T> callback;

  public JsonParserCallback(final AsyncCallback<T> callback) {
    this.callback = callback;
  }

  @Override
  public void onSuccess(final JSONValue result) {
    callback.onSuccess(parse(result));
  }

  protected abstract T parse(JSONValue result);

  @Override
  public void onFailure(final Throwable caught) {
    callback.onFailure(caught);
  }
}
