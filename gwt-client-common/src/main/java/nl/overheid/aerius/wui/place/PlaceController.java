package nl.overheid.aerius.wui.place;

import com.google.inject.ImplementedBy;

@ImplementedBy(PlaceControllerImpl.class)
public interface PlaceController {
  Place getPreviousPlace();

  Place getPlace();

  void goTo(Place place, boolean silent);

  void goTo(Place place);
}
