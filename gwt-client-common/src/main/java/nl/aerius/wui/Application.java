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
package nl.aerius.wui;

import com.google.gwt.core.client.GWT;

/**
 * @implNote For some reason, during development, it might help to modify, save,
 *           and rebuild this class in order for deferred binding to work.
 * 
 *           Noted here because this is inevitably where you'll end up during
 *           debugging.
 */
public class Application {
  public static final Application A = GWT.create(Application.class);

  public void create(final Runnable finish) {
    throw new RuntimeException("No Application implementation injected.");
  }
}
