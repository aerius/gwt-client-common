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
package nl.overheid.aerius.wui.util;

import java.util.Date;

public class Notification {
  public enum Type {
    MESSAGE, WARNING, ERROR;
  }

  private final String message;
  private final Throwable exception;

  private final Type type;

  private final long reference;

  public Notification(final String msg) {
    this(msg, (Throwable) null, Type.MESSAGE);
  }

  public Notification(final Throwable ex) {
    this(null, ex, Type.ERROR);
  }

  public Notification(final String msg, final Throwable exception) {
    this(msg, exception, Type.ERROR);
  }

  public Notification(final Throwable ex, final Type type) {
    this(null, ex, type);
  }

  public Notification(final String msg, final Type type) {
    this(msg, (Throwable) null, type);
  }

  private Notification(final String msg, final Throwable exception, final Type type) {
    this.message = msg;
    this.exception = exception;
    this.type = type;
    this.reference = new Date().getTime();
  }

  public Throwable getException() {
    return exception;
  }

  public String getMessage() {
    return message == null ? exception == null ? "" : exception.getMessage() : message;
  }

  public boolean isError() {
    return type == Type.ERROR;
  }

  public boolean isMessage() {
    return type == Type.MESSAGE;
  }

  public boolean isWarning() {
    return type == Type.WARNING;
  }

  public long getReference() {
    return reference;
  }
}
