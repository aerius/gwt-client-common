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
package nl.aerius.geo.event;

import com.google.gwt.dom.client.Element;
import com.google.web.bindery.event.shared.binder.GenericEvent;

import nl.aerius.geo.BBox;

public class RequestExtentCorrectionEvent extends GenericEvent {
  private BBox box;
  private final Element viewport;

  public RequestExtentCorrectionEvent(final BBox box, final Element viewport) {
    this.box = box;
    this.viewport = viewport;
  }

  public void setCorrectedBox(final BBox box) {
    this.box = box;
  }

  public BBox getValue() {
    return box;
  }

  public BBox getCorrectedBox() {
    return box;
  }

  public Element getViewport() {
    return viewport;
  }
}
