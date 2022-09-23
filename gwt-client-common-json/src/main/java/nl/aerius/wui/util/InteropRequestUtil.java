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
package nl.aerius.wui.util;

import java.util.Map;
import java.util.function.Consumer;

import com.google.gwt.user.client.rpc.AsyncCallback;

import elemental2.core.Global;
import elemental2.dom.FormData;
import elemental2.dom.ProgressEvent;
import elemental2.dom.XMLHttpRequest;

import jsinterop.base.Js;

import nl.aerius.wui.service.exception.RequestClientException;

public class InteropRequestUtil {
  public static <T> void doGet(final String url, final AsyncCallback<T> callback) {
    doRequest("get", url, null, callback);
  }

  public static <T> void doPost(final String url, final AsyncCallback<T> callback) {
    doRequest("post", url, (FormData) null, callback);
  }

  public static <T> void doPost(final String url, final FormData data, final AsyncCallback<T> callback) {
    doRequest("post", url, data, callback);
  }

  public static <T> void doRequest(final String method, final String url, final FormData payload, final AsyncCallback<T> callback) {
    final XMLHttpRequest request = getRequest(method, url, req -> {
      if (req.status != 200) {
        if (req.responseText == null || req.responseText.isEmpty()) {
          handleError(callback, req.status + " > " + req.statusText);
        } else {
          handleError(callback, req.responseText);
        }
      } else {
        final String responseText = req.responseText;
        callback.onSuccess(Js.cast(Global.JSON.parse(responseText)));
      }
    }, null, callback);
    request.send(payload);
  }

  public static <T> void doRequestCustom(final String method, final String url, final FormData payload,
      final Consumer<XMLHttpRequest> listener) {
    final XMLHttpRequest request = getRequest(method, url, listener, null, null);
    request.send(payload);
  }

  public static <T> void doRequestCustom(final String method, final String url, final FormData payload,
      final Consumer<XMLHttpRequest> listener, final Consumer<XMLHttpRequest> manipulator) {
    final XMLHttpRequest request = getRequest(method, url, listener, manipulator, null);
    request.send(payload);
  }

  private static <T> XMLHttpRequest getRequest(final String method, final String url, final Consumer<XMLHttpRequest> listener,
      final Consumer<XMLHttpRequest> manipulator, final AsyncCallback<T> callback) {
    final XMLHttpRequest req = new XMLHttpRequest();

    if (manipulator != null) {
      manipulator.accept(req);
    }

    if (callback != null) {
      req.addEventListener("error", evt -> {
        handleError(callback, "XHR Error: " + evt.type + " (loaded:" + ((ProgressEvent) Js.uncheckedCast(evt)).loaded + ")");
      });
    }

    req.addEventListener("load", evt -> listener.accept(req));

    req.open(method, url);
    return req;
  }

  public static native void log(Object message) /*-{
    console.info(message );
  }-*/;

  private static void handleError(final AsyncCallback<?> callback, final String responseText) {
    callback.onFailure(new RequestClientException(responseText));
  }

  public static FormData createFormData(final Map<String, String> map) {
    final FormData data = new FormData();
    map.forEach((k, v) -> data.append(k, v));
    return data;
  }

  public static String prepareUrl(final String host, final String template, final String... args) {
    if (args.length % 2 != 0) {
      throw new RuntimeException("Template args are of incorrect size: " + args.length);
    }

    String bldr = host + template;
    for (int i = 0; i < args.length; i += 2) {
      if (args[i] == null || args[i + 1] == null) {
        continue;
      }

      bldr = bldr.replaceAll(args[i], args[i + 1]);
    }

    return bldr;
  }
}
