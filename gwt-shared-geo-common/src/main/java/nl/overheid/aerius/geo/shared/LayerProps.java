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
package nl.overheid.aerius.geo.shared;

import java.io.Serializable;

import nl.overheid.aerius.geo.domain.legend.Legend;

/**
 * Properties for a specific Layer. The properties contain additional configuration settings not available in other data.
 */
public class LayerProps implements Serializable {

  private static final long serialVersionUID = 2L;

  private int id;

  /**
   * The reference name of the layer.
   */
  private String name;

  /**
   * The minimum scale at which data will be visible. If scale becomes larger nothing will be shown. If the value is < 0 it means there is no min
   * scale.
   */
  private float minScale;

  /**
   * The maximum scale for which data is available. If scale comes below this value nothing will be shown. If the value is < 0 it means there is no
   * max scale.
   */
  private float maxScale;

  /**
   * The begin year at which data starts becoming visible. If not set, but end year is, everything &lt;= end year is allowed.
   */
  private Integer beginYear;

  /**
   * The end year after which data ceases to exist (we've already passed 2012), so year specific to this layer.
   * If not set, but begin year is, everything &gt;= begin year is allowed.
   */
  private Integer endYear;

  /**
   * Set if a custom legend is present. If null the legend via the capabilities information should be queried.
   */
  private Legend legend;

  /**
   * Opacity setting of the layer.
   */
  private float opacity = 1.0F;

  /**
   * Boolean if the layer is should be shown or just shown in the category list but not enabled.
   */
  private boolean visible;

  /**
   * The title of the layer. Used to set a custom title on external layers.
   */
  private String title;

  /**
   * The url of the layer, or a placeholder to be resolved in the client.
   */
  private String url;

  /**
   * Name under which this layer is bundled.
   */
  private String bundleName;

  /**
   * Attribution of the owner of the layer service.
   */
  private String attribution;

  /**
   * Returns true if the year is within the range of the begin and end year.
   *
   * @param year year to check
   * @return true if year within range
   */
  public boolean containsYear(final int year) {
    return (beginYear == null ? true : year >= beginYear) && (endYear == null ? true : year <= endYear);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public float getMinScale() {
    return minScale;
  }

  public float getMaxScale() {
    return maxScale;
  }

  public Legend getLegend() {
    return legend;
  }

  public float getOpacity() {
    return opacity;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public String getBundleName() {
    return bundleName;
  }

  public void setBundleName(final String bundleName) {
    this.bundleName = bundleName;
  }

  public String getAttribution() {
    return attribution;
  }

  public void setAttribution(final String attribution) {
    this.attribution = attribution;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setMinScale(final float minScale) {
    this.minScale = minScale;
  }

  public void setMaxScale(final float maxScale) {
    this.maxScale = maxScale;
  }

  public void setBeginYear(final Integer beginYear) {
    this.beginYear = beginYear;
  }

  public void setEndYear(final Integer endYear) {
    this.endYear = endYear;
  }

  public void setLegend(final Legend legend) {
    this.legend = legend;
  }

  public void setOpacity(final float opacity) {
    this.opacity = opacity;
  }

  public void setVisible(final boolean visible) {
    this.visible = visible;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "LayerProps [id=" + id + ", name=" + name + ", minScale=" + minScale + ", maxScale=" + maxScale + ", beginYear=" + beginYear + ", endYear="
        + endYear + ", legend=" + legend + ", opacity=" + opacity + ", visible=" + visible + ", title=" + title + ", url=" + url + ", bundleName="
        + bundleName + ", attribution=" + attribution + "]";
  }
}
