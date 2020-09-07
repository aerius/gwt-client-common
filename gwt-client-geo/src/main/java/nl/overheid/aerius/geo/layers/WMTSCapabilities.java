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

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Data object with multiple layers found through a GetCapabilities call to the Web Map Tile Service
 */
public class WMTSCapabilities implements Serializable {

  private static final long serialVersionUID = 8028359474720485972L;

  /**
   * The url to the layer service.
   */
  private String url;

  /**
   * Boolean if this capabilities can be removed by the user from the layer selection panel. The user still has the ability to add/remove indiviual
   * layers within this capabilities from the layer panel.
   */
  private boolean canBeRemoved;
  private ArrayList<LayerProps> layers;
  private String capabilitiesXML;

  public String getCapabilitiesXML() {
    return capabilitiesXML;
  }

  public String getUrl() {
    return url;
  }

  public boolean isCanBeRemoved() {
    return canBeRemoved;
  }

  public ArrayList<LayerProps> getLayers() {
    return layers;
  }

  public void setCapabilitiesXML(final String capabilitiesXML) {
    this.capabilitiesXML = capabilitiesXML;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public void setCanBeRemoved(final boolean canBeRemoved) {
    this.canBeRemoved = canBeRemoved;
  }

  public void setLayers(final ArrayList<LayerProps> layers) {
    this.layers = layers;
  }

  @Override
  public String toString() {
    return "LayerCapabilities [capabilitiesXML=" + (capabilitiesXML == null ? "" : capabilitiesXML.replaceAll("\n", "")) + ",\nurl="
        + url + ", canBeRemoved=" + canBeRemoved + ", layers=" + layers + "]";
  }

  public WMTSCapabilities copy() {
    final WMTSCapabilities layerCapabilities = new WMTSCapabilities();
    layerCapabilities.setCanBeRemoved(isCanBeRemoved());
    layerCapabilities.setCapabilitiesXML(getCapabilitiesXML());
    layerCapabilities.setLayers(getLayers());
    layerCapabilities.setUrl(getUrl());
    return layerCapabilities;
  }
}
