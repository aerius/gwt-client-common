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
package nl.overheid.aerius.geo.layers;

import nl.overheid.aerius.geo.legend.Legend;

/**
 * Properties for a specific Layer. The properties contain additional configuration settings not available in other data.
 */
public class LayerProps {
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
   * Set if a custom legend is present. If null the legend via the capabilities information should be queried.
   */
  private Legend legend;

  /**
   * Opacity setting of the layer.
   */
  private float opacity = 1.0F;

  /**
   * Boolean if the layer should be shown or just shown in the category list but not enabled.
   */
  private boolean enabled;

  /**
   * The title of the layer. Used to set a custom title on external layers.
   */
  private String title;

  private String attribution;

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

  public boolean isEnabled() {
    return enabled;
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

  public void setLegend(final Legend legend) {
    this.legend = legend;
  }

  public void setOpacity(final float opacity) {
    this.opacity = opacity;
  }

  public void setEnabled(final boolean enable) {
    this.enabled = enable;
  }

  @Override
  public String toString() {
    return "LayerProps [id=" + id + ", name=" + name + ", minScale=" + minScale + ", maxScale=" + maxScale + ", legend=" + legend + ", opacity="
        + opacity + ", enabled=" + enabled + "]";
  }
}
