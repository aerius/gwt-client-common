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
package nl.aerius.vuelidate;

import jsinterop.base.JsPropertyMap;

import elemental2.core.JsObject;

public class ValidatorBuilder implements JsPropertyMap<Object> {
  public static ValidatorBuilder create() {
    return new ValidatorBuilder();
  }

  public ValidatorBuilder alpha() {
    return set("alpha", DefaultValidators.alpha);
  }

  public ValidatorBuilder alphaNum() {
    return set("alphaNum", DefaultValidators.alphaNum);
  }

  public ValidatorBuilder and() {
    return set("and", DefaultValidators.and);
  }

  public ValidatorBuilder between() {
    return set("between", DefaultValidators.between);
  }

  public ValidatorBuilder email() {
    return set("email", DefaultValidators.email);
  }

  public ValidatorBuilder decimal() {
    return set("decimal", DefaultValidators.decimal);
  }

  public ValidatorBuilder helpers() {
    return set("helpers", DefaultValidators.helpers);
  }

  public ValidatorBuilder integer() {
    return set("integer", DefaultValidators.integer);
  }

  public ValidatorBuilder ipAddress() {
    return set("ipAddress", DefaultValidators.ipAddress);
  }

  public ValidatorBuilder macAddress() {
    return set("macAddress", DefaultValidators.macAddress);
  }

  public ValidatorBuilder maxLength() {
    return set("maxLength", DefaultValidators.maxLength);
  }

  public ValidatorBuilder maxValue() {
    return set("maxValue", DefaultValidators.maxValue());
  }

  public ValidatorBuilder maxValue(final int num) {
    return set("maxValue", DefaultValidators.maxValue(num));
  }

  public ValidatorBuilder minLength() {
    return set("minLength", DefaultValidators.minLength);
  }

  public ValidatorBuilder minValue() {
    return set("minValue", DefaultValidators.minValue());
  }

  public ValidatorBuilder minValue(final int num) {
    return set("minValue", DefaultValidators.minValue(num));
  }

  public ValidatorBuilder not() {
    return set("not", DefaultValidators.not);
  }

  public ValidatorBuilder numeric() {
    return set("numeric", DefaultValidators.numeric);
  }

  public ValidatorBuilder or() {
    return set("or", DefaultValidators.or);
  }

  public ValidatorBuilder required() {
    return set("required", DefaultValidators.required);
  }

  public ValidatorBuilder requiredIf() {
    return set("requiredIf", DefaultValidators.requiredIf);
  }

  public ValidatorBuilder requiredUnless() {
    return set("requiredUnless", DefaultValidators.requiredUnless);
  }

  public ValidatorBuilder sameAs() {
    return set("sameAs", DefaultValidators.sameAs);
  }

  public ValidatorBuilder url() {
    return set("url", DefaultValidators.url);
  }

  public ValidatorBuilder set(final String key, final JsObject validator) {
    JsPropertyMap.super.set(key, validator);
    return this;
  }
}
