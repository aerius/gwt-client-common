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
package nl.overheid.aerius.application;

import jsinterop.base.Js;

import nl.overheid.aerius.vuelidate.ValidationOptions;
import nl.overheid.aerius.vuelidate.ValidatorBuilder;

public class DemoValidators extends ValidationOptions<DemoComponent> {
  @Override
  protected void constructValidators(final Object options, final ValidatorInstaller v) {
    final DemoComponent instance = Js.uncheckedCast(options);

    v.install("name", () -> instance.name = null, ValidatorBuilder.create().required().alpha());
    v.install("email", () -> instance.email = null, ValidatorBuilder.create().required().email());
    v.install("secondaryEmail", () -> instance.secondaryEmail = null, ValidatorBuilder.create().email());
  }
}
