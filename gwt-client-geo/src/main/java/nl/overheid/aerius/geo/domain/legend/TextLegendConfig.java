package nl.overheid.aerius.geo.domain.legend;

public class TextLegendConfig extends LegendConfig {
  private String text;

  public TextLegendConfig() {
    super(LegendType.TEXT);
  }

  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "TextLegendConfig [text=" + text + ", toString()=" + super.toString() + "]";
  }
}
