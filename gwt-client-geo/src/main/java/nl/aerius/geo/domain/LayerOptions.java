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
package nl.aerius.geo.domain;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import jsinterop.annotations.JsMethod;

/**
 * Provides selectable options to choose from in the layer panel
 */
public class LayerOptions {

  public static final String NO_OPTION_SELECTED = "NO_OPTION_SELECTED";

  /**
   * Options to choose from
   */
  private final List<? extends Object> options;

  /**
   * Function to return the name of these options
   */
  private final Function<Object, String> nameFunction;

  /**
   * Callback function after a option is selected
   */
  private final Consumer<Object> selectionFunction;

  /**
   * Current selected option
   */
  private Object selected;

  public LayerOptions(final List<? extends Object> options, final Function<Object, String> nameFunction, final Consumer<Object> selectionFunction,
      final Object selected) {
    this.options = options;
    this.nameFunction = nameFunction;
    this.selectionFunction = selectionFunction;
    this.selected = selected;
  }

  public static <T> LayerOptions createFromEnum(final T[] e, final Function<T, String> nameFunction, final Consumer<T> selectionFunction,
      final T selected) {
    return new LayerOptions(Arrays.asList(e), (Function<Object, String>) nameFunction, (Consumer<Object>) selectionFunction, selected);
  }

  public List<? extends Object> getOptions() {
    return options;
  }

  public Object getSelected() {
    return selected;
  }

  @JsMethod
  public String getNameOf(final Object object) {
    return nameFunction.apply(object);
  }

  @JsMethod
  public void select(final Object option) {
    this.selected = option;
    selectionFunction.accept(option);
  }
}
