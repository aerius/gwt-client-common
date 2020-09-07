package nl.overheid.aerius.wui.future;

import java.util.function.Consumer;

import com.google.gwt.user.client.rpc.AsyncCallback;

import nl.overheid.aerius.wui.dev.GWTProd;

/**
 * This is an abstract implementation of AsyncCallback which implements the
 * onFailure method.
 *
 * @param <T> The type of the return value that was declared in the synchronous
 *            version of the method. If the return type is a primitive, use the
 *            boxed version of that primitive (for example, an <code>int</code>
 *            return type becomes an {@link Integer} type argument, and a
 *            <code>void</code> return type becomes a {@link Void} type
 *            argument, which is always <code>null</code>).
 */
public class AppAsyncCallback<T> implements AsyncCallback<T> {
  private final Consumer<T> success;
  private final Consumer<Throwable> failure;

  public AppAsyncCallback() {
    this(null, null);
  }

  public AppAsyncCallback(final Consumer<T> success) {
    this(success, null);
  }

  public AppAsyncCallback(final Consumer<T> success, final Consumer<Throwable> failure) {
    this.success = success;
    this.failure = failure;
  }

  @Override
  public void onSuccess(final T result) {
    if (success != null) {
      success.accept(result);
    }
  }

  /**
   * NOTE: If you override this method put A super.onFailure as the LAST statement
   * in the method because this method will throw a RuntimeException and therefore
   * you code after the super call won't be executed.
   *
   * Throws a {@link RuntimeException} with the passed throwable.
   *
   * @param caught actual exception returned from the server
   */
  @Override
  public void onFailure(final Throwable caught) {
    if (failure == null) {
      GWTProd.warn("Falling back to default runtime exception after AppAsyncCallback. Consider changing this.");
      throw new RuntimeException(caught);
    }

    failure.accept(caught);
  }

  public static <T> AppAsyncCallback<T> create() {
    return new AppAsyncCallback<T>();
  }

  public static <T> AppAsyncCallback<T> create(final Consumer<T> success) {
    return new AppAsyncCallback<T>(success);
  }

  public static <T> AppAsyncCallback<T> create(final Consumer<T> success, final Consumer<Throwable> failure) {
    return new AppAsyncCallback<T>(success, failure);
  }
}
