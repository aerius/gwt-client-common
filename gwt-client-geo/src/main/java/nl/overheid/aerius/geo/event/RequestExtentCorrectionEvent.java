package nl.overheid.aerius.geo.event;

import com.google.gwt.dom.client.Element;
import com.google.web.bindery.event.shared.binder.GenericEvent;

import nl.overheid.aerius.geo.BBox;

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
