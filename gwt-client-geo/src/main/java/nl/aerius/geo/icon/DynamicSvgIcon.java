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
package nl.aerius.geo.icon;

public class DynamicSvgIcon {

  private final String svgContent;
  private final double[] anchorInPx;

  public DynamicSvgIcon(String svgContent, double[] anchorInPx) {
    this.svgContent = svgContent;
    this.anchorInPx = anchorInPx;
  }

  public String getSvgContent() {
    return svgContent;
  }

  public String getSrc() {
    return "data:image/svg+xml;base64," + base64encode(getSvgContent());
  }

  public double[] getAnchorInPx() {
    return anchorInPx;
  }

  private static native String base64encode(String a) /*-{
      return window.btoa(a);
  }-*/;

}
