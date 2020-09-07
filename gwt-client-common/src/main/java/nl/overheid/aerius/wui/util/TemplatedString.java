package nl.overheid.aerius.wui.util;

public class TemplatedString {
  private String replacer;

  public TemplatedString(final String origin) {
    this.replacer = origin;
  }

  public void replace(final String target, final String value) {
    replaceClean("{" + target + "}", value);
  }

  public void replaceClean(final String target, final String value) {
    replacer = replacer.replaceAll(target, value);
  }

  @Override
  public String toString() {
    return replacer;
  }
}
