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
package nl.aerius.wui.place;

import java.util.function.Supplier;

public abstract class ApplicationPlace implements TokenizedPlace {
  private final PlaceTokenizer<? extends TokenizedPlace> tokenizer;

  public static class Tokenizer<P extends ApplicationPlace> extends SequenceTokenizer<P> {
    private final Supplier<P> supplier;
    private final String prefix;

    public Tokenizer(final Supplier<P> supplier, final String... parts) {
      this.supplier = supplier;
      this.prefix = String.join("/", parts);
    }

    public static <P extends ApplicationPlace> Tokenizer<P> create(final Supplier<P> supplier, final String... parts) {
      return new Tokenizer<P>(supplier, parts);
    }

    @Override
    public String getPrefix() {
      return prefix;
    }

    @Override
    protected P createPlace() {
      return supplier.get();
    }

    protected static String[] prepend(final String prefix, final String[] post) {
      final String[] newArr = new String[post.length + 1];
      System.arraycopy(post, 0, newArr, 1, post.length);
      newArr[0] = prefix;
      return newArr;
    }
  }

  public ApplicationPlace(final PlaceTokenizer<? extends TokenizedPlace> tokenizer) {
    this.tokenizer = tokenizer;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <P extends TokenizedPlace> PlaceTokenizer<P> getTokenizer() {
    return (PlaceTokenizer<P>) tokenizer;
  }

  @Override
  public String getToken() {
    return tokenizer.getPrefix() + "/" + TokenizedPlace.super.getToken();
  }

  public <E extends ApplicationPlace> E copyTo(final E copy) {
    return copy;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }
}
