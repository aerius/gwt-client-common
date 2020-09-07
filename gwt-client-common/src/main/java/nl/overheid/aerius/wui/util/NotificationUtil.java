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
package nl.overheid.aerius.wui.util;

import com.google.web.bindery.event.shared.EventBus;

import nl.overheid.aerius.wui.event.NotificationEvent;
import nl.overheid.aerius.wui.util.Notification.Type;

public final class NotificationUtil {
  private NotificationUtil() {}

  public static void broadcastMessage(final EventBus eventBus, final String message) {
    eventBus.fireEvent(new NotificationEvent(new Notification(message)));
  }

  public static void broadcastError(final EventBus eventBus, final String message) {
    eventBus.fireEvent(new NotificationEvent(new Notification(message, Type.ERROR)));
  }

  public static void broadcastWarning(final EventBus eventBus, final String message) {
    eventBus.fireEvent(new NotificationEvent(new Notification(message, Type.WARNING)));
  }
}
