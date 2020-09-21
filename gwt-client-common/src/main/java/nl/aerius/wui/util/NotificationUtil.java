/*
 * Copyright Dutch Ministry of Agriculture, Nature and Food Quality
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

import com.google.web.bindery.event.shared.EventBus;

import nl.aerius.wui.event.NotificationEvent;
import nl.aerius.wui.util.Notification.Type;

/**
 * Sugar for broadcasting Notification objects.
 */
public final class NotificationUtil {
  private NotificationUtil() {
  }

  public static void broadcastMessage(final EventBus eventBus, final String txt) {
    broadcast(eventBus, new Notification(txt));
  }

  /**
   * Broadcasts a message. Text. Not an error.
   *
   * @param eventBus the bus to broadcast over
   * @param txt the message text to broadcast
   * @param url additional url
   */
  public static void broadcastMessage(final EventBus eventBus, final String title, final String txt) {
    broadcast(eventBus, new Notification(title, txt));
  }

  /**
   * Broadcasts a message. Text. Not an error.
   *
   * @param eventBus the bus to broadcast over
   * @param txt the message text to broadcast
   * @param url additional url
   */
  public static void broadcastMessage(final EventBus eventBus, final String title, final String txt, final String url) {
    broadcast(eventBus, new Notification(title, txt, url));
  }

  /**
   * Broadcasts an error. Text. Not a message.
   *
   * @param eventBus the bus to broadcast over
   * @param txt the error text to broadcast
   */
  public static void broadcastError(final EventBus eventBus, final String txt) {
    broadcast(eventBus, new Notification(txt, Type.ERROR));
  }

  /**
   * Broadcasts an error. Text. Not a message.
   *
   * @param eventBus the bus to broadcast over
   * @param txt the error text to broadcast
   */
  public static void broadcastError(final EventBus eventBus, final String title, final String txt) {
    broadcast(eventBus, new Notification(title, txt, Type.ERROR));
  }

  /**
   * Broadcasts an error. Exception.
   *
   * @param eventBusthe bus to broadcast over
   * @param error the error to broadcast
   */
  public static void broadcastError(final EventBus eventBus, final Throwable error) {
    broadcast(eventBus, new Notification(error));
  }

  /**
   * Broadcasts a warning. Exception.
   *
   * @param eventBus the bus to broadcast over
   * @param error the error to broadcast
   */
  public static void broadcastWarning(final EventBus eventBus, final String message) {
    broadcast(eventBus, new Notification(message, Type.WARNING));
  }

  /**
   * Broadcasts a warning. Exception.
   *
   * @param eventBus the bus to broadcast over
   * @param error the error to broadcast
   */
  public static void broadcastWarning(final EventBus eventBus, final String title, final String message) {
    broadcast(eventBus, new Notification(title, message, Type.WARNING));
  }

  /**
   * Broadcasts a warning. Exception.
   *
   * @param eventBus the bus to broadcast over
   * @param error the error to broadcast
   */
  public static void broadcastWarning(final EventBus eventBus, final Throwable error) {
    broadcast(eventBus, new Notification(error, Type.WARNING));
  }

  /**
   * Broadcasts a {@link Notification}.
   *
   * @param eventBus the bus to broadcast over
   * @param notification the object to broadcast
   */
  public static void broadcast(final EventBus eventBus, final Notification notification) {
    broadcast(eventBus, new NotificationEvent(notification));
  }

  private static void broadcast(final EventBus eventBus, final NotificationEvent notification) {
    eventBus.fireEvent(notification);
  }
}
