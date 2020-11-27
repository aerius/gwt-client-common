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
package nl.aerius.geo.legend;

import java.io.Serializable;

/**
 * TODO There's currently no good way to localize this stuff. Ideally it _is_ done on the server, because layers are all coming from the server (or
 * otherwise externally) and the client need not know anything about them. All i18n stuff is stored either in the database, or the web server. This
 * object is typically constructed in _common_, which only has access to the database i18n, I'm reluctant to put legend information in there (layer
 * config isn't in there either, yet)
 */
public class TextLegend implements Legend, Serializable {
  private static final long serialVersionUID = -7917547145929908026L;

  private String text;

  public TextLegend() {}

  public TextLegend(final String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "TextLegend [text=" + text + "]" + super.toString();
  }
}
