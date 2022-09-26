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

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class JsonRequestCallback extends SimpleDebuggableRequest implements RequestCallback {
  private final AsyncCallback<JSONValue> callback;

  private JsonRequestCallback(final AsyncCallback<JSONValue> callback) {
    this.callback = callback;
  }

  @Override
  public void onResponseReceived(final Request request, final Response response) {
    if (validateResponse(request, response)) {
      return;
    }

    try {
      final JSONValue parsed = JSONParser.parseStrict(response.getText());
      try {
        callback.onSuccess(parsed);
      } catch (final Exception e) {
        callback.onFailure(e);
      }
    } catch (final IllegalArgumentException e) {
      callback.onFailure(new RuntimeException("Failure while parsing json: " + origin));
    }
  }

  @Override
  public void onError(final Request request, final Throwable exception) {
    callback.onFailure(exception);
  }

  public static RequestCallback create(final AsyncCallback<JSONValue> callback) {
    return new JsonRequestCallback(callback);
  }
}
