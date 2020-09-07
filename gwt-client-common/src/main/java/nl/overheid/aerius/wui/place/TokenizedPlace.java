package nl.overheid.aerius.wui.place;

public interface TokenizedPlace extends Place {
  <P extends TokenizedPlace> PlaceTokenizer<P> getTokenizer();

  default String getToken() {
    return getTokenizer().getToken(this);
  }
}
