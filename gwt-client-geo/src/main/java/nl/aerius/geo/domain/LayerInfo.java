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
package nl.aerius.geo.domain;

import java.util.Optional;

import nl.aerius.geo.domain.legend.Legend;

public class LayerInfo {
  private String bundle = null;
  private String cluster = null;
  private String friendlyName = null;

  private String name = null;
  private String title = null;

  private LayerOptions[] options = null;
  private Legend legend = null;

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

  public LayerOptions[] getOptions() {
    return options;
  }

  public void setOptions(final LayerOptions[] options) {
    this.options = options;
  }

  public Legend getLegend() {
    return legend;
  }

  public void setLegend(final Legend legend) {
    this.legend = legend;
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
