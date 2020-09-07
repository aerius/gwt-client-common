/*
 * Copyright Dutch Ministry of Agriculture, Nature and Food Quality
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
package nl.overheid.aerius.geo.domain;

import java.util.List;
import java.util.Optional;

import nl.overheid.aerius.geo.domain.legend.LegendConfig;

public class LayerInfo {
  private String bundle;
  private String cluster;
  private String friendlyName;

  private String name;
  private String title;

  private LegendConfig legendConfig;

  private List<String> selectables;

  public LayerInfo() {}

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

  public LegendConfig getLegendConfig() {
    return legendConfig;
  }

  public void setLegendConfig(final LegendConfig legendConfig) {
    this.legendConfig = legendConfig;
  }

  public List<String> getSelectables() {
    return selectables;
  }

  public void setSelectables(final List<String> selectables) {
    this.selectables = selectables;
  }

  public boolean isBundle() {
    return bundle != null;
  }

  public String getBundle() {
    return bundle;
  }

  public void setBundle(final String bundle) {
    this.bundle = bundle;
  }

  public Optional<String> getCluster() {
    return Optional.ofNullable(cluster);
  }

  public void setCluster(final String cluster) {
    this.cluster = cluster;
  }

  public Optional<String> getFriendlyName() {
    return Optional.ofNullable(friendlyName);
  }

  public void setFriendlyName(final String friendlyName) {
    this.friendlyName = friendlyName;
  }
}
