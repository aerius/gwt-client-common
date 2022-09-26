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
package nl.aerius.vuelidate.util;

import java.util.function.Consumer;

/**
 * Helper class for setting handling validations.
 */
public final class ValidationUtil {

  private ValidationUtil() {
    // Util class
  }

  /**
   * Sets the String input safely on a double field in a complex object.
   * Example usage:
   *
   * <code>
   * @Computed
   * protected void setSomeData(final String someData) {
   *   ValidationUtil.setSafeDoubleValue(copmlexObject::someData, someData, 0.0);
   *   someDataV = someData;
   * }
   * </code>
   * @param consumer method to set the new value on
   * @param value value to set
   * @param _default default value in case the input value is null or empty
   */
  public static void setSafeDoubleValue(final Consumer<Double> consumer, final String value, final double _default) {
    try {
      if (value == null || value.isEmpty()) {
        consumer.accept(_default);
      } else {
        final double doubleValue = Double.valueOf(value);
        if (!Double.isNaN(doubleValue)) {
          consumer.accept(doubleValue);
        }
      }
    } catch (final NumberFormatException e) {
    }
  }

  /**
   * Sets the String input safely on a int field in a complex object.
   * Example usage:
   *
   * <code>
   * @Computed
   * protected void setSomeData(final String someData) {
   *   ValidationUtil.setSafeIntegerValue(copmlexObject::someData, someData, 0.0);
   *   someDataV = someData;
   * }
   * </code>
   * @param consumer method to set the new value on
   * @param value value to set
   * @param _default default value in case the input value is null or empty
   */
  public static void setSafeIntegerValue(final Consumer<Integer> consumer, final String value, final int _default) {
    try {
      if (value == null || value.isEmpty()) {
        consumer.accept(_default);
      } else {
        consumer.accept(Integer.valueOf(value));
      }
    } catch (final NumberFormatException e) {
    }
  }
}
