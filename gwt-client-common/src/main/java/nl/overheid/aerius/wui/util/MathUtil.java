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
package nl.overheid.aerius.wui.util;

/**
 * Math class specific for GWT.
 */
public final class MathUtil {

  private static final double ROUND_UP = 0.5;

  private MathUtil() {
    // util class.
  }

  /**
   * Java uses the if-you-ask-me-incorrect-and-dumb-version of %, which yields a negative output for negative input.
   *
   * Ie. -3 % 2 yields -1, while it should yield 1. This is a correct implementation.
   *
   * @param a
   *          Input
   * @param b
   *          Mod
   * @return Positive output
   */
  public static int positiveMod(final int a, final int b) {
    final int mod = a % b;
    return mod < 0 ? mod + b : mod;
  }

  /**
   * Round a double to an int. Not using standard Math.round because that returns a long and long is not nicely supported in JavaScript.
   * @param value value to round
   * @return value rounded to int
   */
  public static int round(final double value) {
    return (int) (value + (value < 0 ? -ROUND_UP : ROUND_UP));
  }
}