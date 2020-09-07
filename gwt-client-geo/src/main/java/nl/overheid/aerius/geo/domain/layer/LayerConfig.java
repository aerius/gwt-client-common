package nl.overheid.aerius.geo.domain.layer;

import java.util.List;

import nl.overheid.aerius.geo.domain.legend.LegendConfig;

/**
 * GEO package
 */
public class LayerConfig {
  public static enum LayerType {
    WMS, WTMS, TMS, WFS;
  }

  private String name;
  private String title;

  private LayerType type;

  private double opacity = 1.0D;
  private boolean visible = true;

  private LegendConfig legend;

  private List<String> selectables;

  private String bundleName;
  private String clusterName;
  private String friendlyName;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public LayerConfig() {}

  public double getOpacity() {
    return opacity;
  }

  public void setOpacity(final double opacity) {
    this.opacity = opacity;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(final boolean visible) {
    this.visible = visible;
  }

  public LayerType getType() {
    return type;
  }

  public void setType(final LayerType type) {
    this.type = type;
  }

  public LegendConfig getLegend() {
    return legend;
  }

  public void setLegend(final LegendConfig legend) {
    this.legend = legend;
  }

  public List<String> getSelectables() {
    return selectables;
  }

  public void setSelectables(final List<String> selectables) {
    this.selectables = selectables;
  }

  public String getBundleName() {
    return bundleName;
  }

  public void setBundleName(final String bundleName) {
    this.bundleName = bundleName;
  }

  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public String getFriendlyName() {
    return friendlyName;
  }

  public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }
}
