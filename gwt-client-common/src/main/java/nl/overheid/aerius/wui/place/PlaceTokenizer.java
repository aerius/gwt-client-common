package nl.overheid.aerius.wui.place;

/**
 * Implemented by objects responsible for text serialization and deserialization of Place objects.
 *
 * @param <P>
 *          a subtype of {@link ApplicationPlace}
 */
public interface PlaceTokenizer<P extends TokenizedPlace> {
  default String getPrefix() {
    return "";
  }

  /**
   * Returns the {@link ApplicationPlace} associated with the given token.
   *
   * @param token
   *          a String token
   * @return a {@link ApplicationPlace} of type P
   */
  P getPlace(String token);

  /**
   * Returns the token associated with the given {@link ApplicationPlace}.
   *
   * @param place
   *          a {@link ApplicationPlace} of type P
   * @return a String token
   */
  String getToken(P place);
}
