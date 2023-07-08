package site.kongdroid.api.util;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

/**
 * Singleton supplier
 *
 * @param <T> Target type
 */
@RequiredArgsConstructor(staticName = "with")
@SuppressWarnings("PMD.UnusedPrivateField")
public final class SingletonSupplier<T> implements Supplier<T> {

  private @NonNull Supplier<T> supplier;

  @Getter(value = PRIVATE, lazy = true)
  private final T instance = supplier.get();

  @Override
  public T get() {
    return getInstance();
  }

}
