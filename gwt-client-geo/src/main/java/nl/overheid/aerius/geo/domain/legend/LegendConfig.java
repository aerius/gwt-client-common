package nl.overheid.aerius.geo.domain.legend;

/**
 * TODO This probably shouldn't have a LegendType enum, should all be text. Not sure how to design this correctly.
 *
 * But, works just fine for the foreseeable future.
 */
public class LegendConfig {
  public static enum LegendType {
    TEXT, COMPONENT;
  }

  private final LegendType type;

  public LegendConfig(final LegendType type) {
    this.type = type;
  }

  public LegendType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "LegendConfig [type=" + type + "]";
  }
}
