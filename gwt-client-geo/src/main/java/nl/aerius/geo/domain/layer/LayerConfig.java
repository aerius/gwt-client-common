/*
 * Copyright the State of the Netherlands
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package nl.aerius.geo.domain.layer;

import java.util.List;

import nl.aerius.geo.domain.legend.LegendConfig;

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
