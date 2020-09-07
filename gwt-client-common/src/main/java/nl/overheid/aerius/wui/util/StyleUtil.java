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

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * Util class to set style properties on widgets/elements.
 */
public class StyleUtil {
  /**
   * Set the text as placeholder property on a html element.
   *
   * @param vbb Widget to set placeholder
   * @param text Text to set as placeholder
   */
  public static void setPlaceHolder(final ValueBoxBase<?> vbb, final String text) {
    vbb.getElement().setAttribute("placeholder", text);
  }
  /**
   * Set the text as placeholder property on a html element.
   *
   * @param vbb Widget to set placeholder
   * @param text Text to set as placeholder
   */
  public static void setPlaceHolder(final TextBox box, final String text) {
    box.getElement().setAttribute("placeholder", text);
  }
}