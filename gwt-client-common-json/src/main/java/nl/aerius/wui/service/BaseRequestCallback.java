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

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

import nl.aerius.wui.service.exception.RequestBlockedException;
import nl.aerius.wui.service.exception.RequestClientException;
import nl.aerius.wui.service.exception.RequestServerException;

public abstract class BaseRequestCallback implements RequestCallback {
  public boolean validateResponse(final Request request, final Response response) {
    if (response.getStatusCode() == 200) {
      return false;
    }

    final String responseCodeString = String.valueOf(response.getStatusCode());

    if (responseCodeString.equals("0")) {
      onError(request, new RequestBlockedException("Client blocked request."));
    } else if (responseCodeString.startsWith("4")) {
      onError(request, new RequestClientException(responseCodeString + " " + response.getStatusText()));
    } else if (responseCodeString.startsWith("5")) {
      onError(request, new RequestServerException(responseCodeString + " " + response.getStatusText()));
    }

    return true;
  }
}
