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
package nl.overheid.aerius.geo.epsg;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Proxy class to get an EPSG object.
 */
public final class EPSGProxy {

  private static final Map<Integer, EPSG> MAP;
  static {
    final Map<Integer, EPSG> map = new HashMap<>();
    map.put(EPSGRDNew.SRID, new EPSGRDNew());
    map.put(EPSGBNGrid.SRID, new EPSGBNGrid());
    MAP = Collections.unmodifiableMap(map);
  }

  private EPSGProxy() {
    // util class
  }

  public static EPSG defaultEpsg() {
    return MAP.get(EPSGRDNew.SRID);
  }

  public static EPSG getEPSG(final int srid) {
    final EPSG epsg = MAP.get(srid);
    return epsg == null ? defaultEpsg() : epsg;
  }
}
