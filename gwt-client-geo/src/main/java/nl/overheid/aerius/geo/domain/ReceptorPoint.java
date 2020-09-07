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

import nl.overheid.aerius.wui.util.HasId;

public class ReceptorPoint extends Point implements HasId {
  private final int id;

  /**
   * Initializes this ReceptorPoint with the given location.
   *
   * @param x x coordinate
   * @param y y coordinate
   * @param id the ID
   */
  public ReceptorPoint(final int id, final double x, final double y) {
    super(x, y);

    this.id = id;
  }

  /**
   * Objects are equal if same type and same id or if obj is a point and the super is equal.
   */
  @Override
  public boolean equals(final Object obj) {
    return obj instanceof ReceptorPoint ? id == ((ReceptorPoint) obj).getId() : super.equals(obj);
  }

  @Override
  public int hashCode() {
    return id;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    return "ReceptorPoint [id=" + id + super.toString() + "]";
  }
}