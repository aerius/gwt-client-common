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

public abstract class AbstractLayerItem implements LayerItem {
  IsLayer<?> layer = null;

  boolean visible = false;
  double opacity = 0D;

  public void setLayer(final IsLayer<?> layer) {
    this.layer = layer;
  }

  @Override
  public IsLayer<?> getLayer() {
    return layer;
  }

  @Override
  public double getOpacity() {
    return opacity;
  }

  @Override
  public void setOpacity(final IsLayer<?> layer, final double opacity) {
    this.opacity = opacity;
  }

  @Override
  public boolean isVisible() {
    return visible;
  }

  @Override
  public boolean isClustered() {
    return layer.getInfo().getCluster().isPresent();
  }

  @Override
  public void setVisible(final IsLayer<?> layer, final boolean visible) {
    this.visible = visible;
  }
}
