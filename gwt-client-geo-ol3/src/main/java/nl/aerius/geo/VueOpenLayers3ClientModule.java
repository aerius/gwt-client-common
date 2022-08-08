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
package nl.aerius.geo;

import com.google.gwt.inject.client.AbstractGinModule;

import nl.aerius.geo.wui.Map;
import nl.aerius.geo.wui.OL3MapComponent;

/**
 * Gin bindings for the Monitor Up application.
 */
public class VueOpenLayers3ClientModule extends AbstractGinModule {
  @Override
  protected void configure() {
    bind(Map.class).to(OL3MapComponent.class);
  }
}
