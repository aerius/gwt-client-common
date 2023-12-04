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
        final double doubleValue = Double.parseDouble(value);

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

  /**
   * Sets the String input safely on a (optional) Double field in a complex object.
   *
   * Note: while an Integer version of this method might be expected, this does not work well with GWT.
   * In javascript, the Integer will not be treated as a 'number', and subsequent serialization to json will mean a object wrapping the actual integer.
   * So while a Double optional field works, a Integer optional field does not.
   * A (suboptimal) way to work around this is to use a Double field in the vue-gwt context,
   * and have proper validation in place to ensure it's actually an integer.
   *
   * Example usage:
   *
   * <code>
   * @Computed
   * protected void setSomeData(final String someData) {
   *   ValidationUtil.setSafeDoubleOptionalValue(copmlexObject::someData, someData);
   *   someDataV = someData;
   * }
   * </code>
   * @param consumer method to set the new value on
   * @param value value to set.
   * If the value is null or an empty string, null will be used as a value supplied to the consumer.
   */
  public static void setSafeDoubleOptionalValue(final Consumer<Double> consumer, final String value) {
    try {
      if (value == null || value.isEmpty()) {
        consumer.accept(null);
      } else {
        final double doubleValue = Double.parseDouble(value);
        if (!Double.isNaN(doubleValue)) {
          consumer.accept(doubleValue);
        }
      }
    } catch (final NumberFormatException e) {
    }
  }
}
