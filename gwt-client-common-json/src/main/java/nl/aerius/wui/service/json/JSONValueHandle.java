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
package nl.aerius.wui.service.json;

import com.google.gwt.json.client.JSONValue;

public class JSONValueHandle {
  private final JSONValue inner;

  public JSONValueHandle(final JSONValue inner) {
    this.inner = inner;
  }

  public JSONValue getInner() {
    return inner;
  }

  public boolean isString() {
    return inner.isString() != null;
  }

  public String asString() {
    return inner.isString().stringValue();
  }

  public Integer asInteger() {
    return (int) inner.isNumber().doubleValue();
  }

  public Double asNumber() {
    return inner.isNumber().doubleValue();
  }

  public boolean isObject() {
    return inner.isObject() != null;
  }

  public JSONObjectHandle asObjectHandle() {
    return new JSONObjectHandle(inner.isObject());
  }

  public boolean isArray() {
    return inner.isArray() != null;
  }

  public JSONArrayHandle asArray() {
    return new JSONArrayHandle(inner.isArray());
  }

  public boolean isNumber() {
    return inner.isNumber() != null;
  }

  public boolean isBoolean() {
    return inner.isBoolean() != null;
  }

  public boolean isNull() {
    return inner.isNull() != null;
  }
}
