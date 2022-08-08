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
package nl.aerius.geo.wui.util;

import java.util.ArrayList;
import java.util.List;

import ol.layer.Image;
import ol.source.ImageWms;

import nl.aerius.geo.domain.LayerOptions;
import nl.aerius.geo.shared.LayerMultiWMSProps;

public final class OL3MultiWMSLayerUtil {

  private static final String MULTI_WMS_VIEW_PARAM = "type";

  private OL3MultiWMSLayerUtil() {
  }

  /**
   * Generates a LayerOptions object for a LayerMultiWMSProps.
   * For each type in the MultiWMS an option in the LayerOptions is created.
   * In addition a "no option selected" option is added.
   *
   * @param conf
   * @param image
   * @return
   */
  public static LayerOptions[] getLayerOptions(final LayerMultiWMSProps conf, final Image image) {

    final ImageWms imageWms = image.getSource();

    final List<Object> options = new ArrayList<>();
    options.add(LayerOptions.NO_OPTION_SELECTED);
    options.addAll(conf.getTypes().keySet());

    return new LayerOptions[] {
        new LayerOptions(
            options,
            key -> conf.getTypes().get(key).toString(),
            key -> {
              if (key == LayerOptions.NO_OPTION_SELECTED) {
                imageWms.getParams().setViewParams("");
              } else {
                imageWms.getParams().setViewParams(MULTI_WMS_VIEW_PARAM + ":" + key);
              }
              imageWms.refresh();
            },
            LayerOptions.NO_OPTION_SELECTED
        )
    };
  }

}
