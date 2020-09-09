package nl.aerius.geo.domain.layer;

import java.util.Optional;

import nl.aerius.wui.replacing.IsActivatorInfo;

public class LayerStyleActivator implements IsActivatorInfo {
  private final String name;

  private final String layer;
  private final String style;

  public LayerStyleActivator(final String name, final String layer, final String style) {
    this.name = name;
    this.layer = layer;
    this.style = style;
  }

  public String getStyle() {
    return style;
  }

  public Optional<String> getLayer() {
    return Optional.ofNullable(layer);
  }

  public static LayerStyleActivator create(final String name, final String layer, final String style) {
    return new LayerStyleActivator(name, layer, style);
  }

  @Override
  public String getName() {
    return name;
  }
}