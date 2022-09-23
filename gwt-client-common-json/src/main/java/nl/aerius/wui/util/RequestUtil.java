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

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import com.google.gwt.user.client.rpc.AsyncCallback;

import elemental2.dom.Blob;
import elemental2.dom.BlobPropertyBag;
import elemental2.dom.FormData;
import elemental2.dom.XMLHttpRequest;

import nl.aerius.wui.exception.HttpRequestException;

public class RequestUtil {

  private RequestUtil() {
    // Util class
  }

  /** GET **/

  public static <T> void doGet(final String url, final Function<AsyncCallback<T>, AsyncCallback<String>> parser, final AsyncCallback<T> callback) {
    doGet(url, null, parser, callback);
  }

  public static <T> void doGet(final String url, final Map<String, String> queryString,
      final Function<AsyncCallback<T>, AsyncCallback<String>> parser, final AsyncCallback<T> callback) {
    doGet(url, queryString, parser.apply(callback));
  }

  public static void doGet(final String url, final AsyncCallback<String> callback) {
    doGet(url, (Map<String, String>) null, callback);
  }

  public static void doGet(final String url, final Map<String, String> queryString, final AsyncCallback<String> callback) {
    doGet(url, queryString, Collections.emptyMap(), callback);
  }

  public static void doGetWithHeaders(final String url, final Map<String, String> additionalHeaders, final AsyncCallback<String> callback) {
    doGet(url, (Map<String, String>) null, additionalHeaders, callback);
  }

  public static void doGet(final String url, final Map<String, String> queryString, final Map<String, String> additionalHeaders,
      final AsyncCallback<String> callback) {
    doRequest("GET", url + format(queryString), additionalHeaders, callback);
  }

  /** POST **/

  public static <T> void doPost(final String url, final Function<AsyncCallback<T>, AsyncCallback<String>> parser, final AsyncCallback<T> callback) {
    doPost(url, null, parser, callback);
  }

  public static void doPost(final String url, final AsyncCallback<String> callback) {
    doPost(url, (FormData) null, callback);
  }

  public static <T> void doPost(final String url, final FormData payload, final Function<AsyncCallback<T>, AsyncCallback<String>> parser,
      final AsyncCallback<T> callback) {
    doPost(url, payload, parser.apply(callback));
  }

  public static void doPost(final String url, final FormData payload, final AsyncCallback<String> callback) {
    doPost(url, payload, Collections.emptyMap(), callback);
  }

  public static void doPost(final String url, final FormData payload, final Map<String, String> additionalHeaders,
      final AsyncCallback<String> callback) {
    doRequest("POST", url, payload, additionalHeaders, callback);
  }

  /**
   * Post a payload to a URL. The payload is expected to be stringified JSON.
   */
  public static void doPost(final String url, final String payload, final AsyncCallback<String> callback) {
    doPost(url, payload, Collections.emptyMap(), callback);
  }

  /**
   * Post a payload to a URL. The payload is expected to be stringified JSON.
   */
  public static void doPost(final String url, final String payload, final Map<String, String> additionalHeaders,
      final AsyncCallback<String> callback) {
    doRequest("POST", url, payload, additionalHeaders, callback);
  }

  /** DELETE **/

  public static void doDelete(final String url, final AsyncCallback<String> callback) {
    doDelete(url, Collections.emptyMap(), callback);
  }

  public static void doDelete(final String url, final Map<String, String> additionalHeaders, final AsyncCallback<String> callback) {
    doRequest("DELETE", url, additionalHeaders, callback);
  }

  /** REQUEST **/

  private static void doRequest(final String method, final String url, final Map<String, String> additionalHeaders,
      final AsyncCallback<String> callback) {
    final XMLHttpRequest req = prepareRequest(method, url, additionalHeaders, callback);
    req.send();
  }

  private static void doRequest(final String method, final String url, final FormData payload, final Map<String, String> additionalHeaders,
      final AsyncCallback<String> callback) {
    final XMLHttpRequest req = prepareRequest(method, url, additionalHeaders, callback);
    req.send(payload);
  }

  private static void doRequest(final String method, final String url, final String payload, final Map<String, String> additionalHeaders,
      final AsyncCallback<String> callback) {
    final XMLHttpRequest req = prepareRequest(method, url, additionalHeaders, callback);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(payload);
  }

  private static XMLHttpRequest prepareRequest(final String method, final String url, final Map<String, String> additionalHeaders,
      final AsyncCallback<String> callback) {
    final XMLHttpRequest req = new XMLHttpRequest();

    addEventListeners(req, callback);

    req.open(method, url);
    additionalHeaders.forEach(req::setRequestHeader);
    return req;
  }

  private static void addEventListeners(final XMLHttpRequest req, final AsyncCallback<String> callback) {
    req.addEventListener("error", evt -> handleError(callback, req.responseText));
    req.addEventListener("load", evt -> {
      if (req.status == 200) {
        callback.onSuccess(req.responseText);
      } else {
        handleError(callback, req.responseText);
      }
    });
  }

  private static void handleError(final AsyncCallback<String> callback, final String responseText) {
    callback.onFailure(new HttpRequestException(responseText));
  }

  private static String format(final Map<String, String> queryString) {
    final StringBuilder bldr = new StringBuilder("?");
    if (queryString != null) {
      queryString.forEach((k, v) -> bldr.append(k + "=" + v + "&"));
    }

    // Prune the last (either a & or ?)
    bldr.setLength(bldr.length() - 1);

    return bldr.toString();
  }

  public static String prepareUrl(final String host, final String template, final String... args) {
    if (args.length % 2 != 0) {
      throw new RuntimeException("Template args are of incorrect size: " + args.length);
    }

    final TemplatedString bldr = new TemplatedString(host + template);
    for (int i = 0; i < args.length; i += 2) {
      bldr.replace(args[i], args[i + 1]);
    }

    return bldr.toString();
  }

  public static Blob createJsonBlob(final String json) {
    final BlobPropertyBag blobPropertyBag = BlobPropertyBag.create();
    blobPropertyBag.setType("application/json");
    return new Blob(new Blob.ConstructorBlobPartsArrayUnionType[] {Blob.ConstructorBlobPartsArrayUnionType.of(json)}, blobPropertyBag);
  }
}
