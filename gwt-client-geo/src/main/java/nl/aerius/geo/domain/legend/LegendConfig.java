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
package nl.aerius.geo.domain.legend;

/**
 * TODO This probably shouldn't have a LegendType enum, should all be text. Not sure how to design this correctly.
 *
 * But, works just fine for the foreseeable future.
 */
public class LegendConfig {
  public static enum LegendType {
    TEXT, COMPONENT;
  }

  private final LegendType type;

  public LegendConfig(final LegendType type) {
    this.type = type;
  }

  public LegendType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "LegendConfig [type=" + type + "]";
  }
}
