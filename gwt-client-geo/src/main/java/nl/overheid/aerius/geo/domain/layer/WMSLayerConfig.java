package nl.overheid.aerius.geo.domain.layer;

import java.util.List;
import java.util.Optional;

/**
 * GEO package
 */
public class WMSLayerConfig extends LayerConfig {
  private String url;

  private String style;
  private String layer;
  private String format;

  private String viewParams;
  private String cql;

  private List<LayerStyleActivator> activators;

  public WMSLayerConfig() {
    setType(LayerType.WMS);
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public String getLayer() {
    return layer;
  }

  public void setLayer(final String layers) {
    this.layer = layers;
  }

  public Optional<String> getViewParams() {
    return Optional.ofNullable(viewParams);
  }

  public void setViewParams(final String viewParams) {
    this.viewParams = viewParams;
  }

  public String getCql() {
    return cql;
  }

  public void setCql(final String cql) {
    this.cql = cql;
  }

  public Optional<List<LayerStyleActivator>> getActivators() {
    return Optional.ofNullable(activators);
  }

  public void setActivators(final List<LayerStyleActivator> activators) {
    this.activators = activators;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(final String style) {
    this.style = style;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(final String format) {
    this.format = format;
  }
}
