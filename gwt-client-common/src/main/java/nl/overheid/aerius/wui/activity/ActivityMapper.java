package nl.overheid.aerius.wui.activity;

import nl.overheid.aerius.wui.place.Place;

/**
 * Finds the activity to run for a given {@link Place}, used to configure an {@link ActivityManager}.
 */
public interface ActivityMapper<C> {
  /**
   * Returns the activity to run for the given {@link Place}, or null.
   *
   * @param place a Place object
   */
  Activity<?, C> getActivity(Place place);
}
