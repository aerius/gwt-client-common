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

/**
 * Represents a notification entry as in the NotificationPanel.
 */
public class Notification {
  public enum Type {
    MESSAGE, WARNING, ERROR;
  }

  private final String message;
  private final String url;
  private final Throwable exception;
  private final Type type;
  private final Date dateTime;

  public Notification(final String msg) {
    this(msg, null, null, Type.MESSAGE);
  }

  public Notification(final Throwable ex) {
    this(null, null, ex, Type.ERROR);
  }

  public Notification(final String msg, final String url) {
    this(msg, url, (Throwable) null, Type.MESSAGE);
  }

  @Deprecated
  public Notification(final String msg, final boolean isError) {
    this(msg, null, null, Type.ERROR);
  }

  public Notification(final String msg, final Throwable exception) {
    this(msg, null, exception, Type.ERROR);
  }

  public Notification(final Throwable ex, final Type type) {
    this(null, null, ex, type);
  }

  public Notification(final String msg, final Type type) {
    this(msg, null, (Throwable) null, type);
  }

  private Notification(final String msg, final String url, final Throwable exception, final Type type) {
    this.message = msg;
    this.url = url;
    this.exception = exception;
    this.type = type;
    dateTime = new Date();
  }

  public Throwable getException() {
    return exception;
  }

  public String getMessage() {
    return message == null ? exception == null || exception.getMessage() == null ? "" : exception.getMessage() : message;
  }

  public String getUrl() {
    return url;
  }

  public boolean isError() {
    return type == Type.ERROR;
  }

  public boolean isMessage() {
    return type == Type.MESSAGE;
  }

  public boolean hasMessage() {
    return message != null;
  }

  public boolean isWarning() {
    return type == Type.WARNING;
  }

  public Date getDateTime() {
    return dateTime;
  }

  @Override
  public String toString() {
    return "Notification [message=" + message + ", exception=" + exception + ", type=" + type + "]";
  }
}
