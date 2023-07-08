package site.kongdroid.api.util;

import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;

import java.util.function.Function;
import java.util.function.Supplier;

import static io.vavr.API.Try;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.beans.factory.BeanFactoryUtils.beanOfTypeIncludingAncestors;
import static org.springframework.beans.factory.BeanFactoryUtils.beansOfTypeIncludingAncestors;

@NoArgsConstructor(access = PRIVATE)
public final class BeanSuppliers{

  /**
   * @throws NoSuchBeanDefinitionException if no bean of the given type was found
   * @throws NoUniqueBeanDefinitionException if more than one bean of the given type was found
   * @throws BeansException if the bean could not be created
   */
  public static <T> Supplier<T> beanSupplier(ListableBeanFactory beanFactory, Class<T> type) {
    return SingletonSupplier.with(() -> beanOfTypeIncludingAncestors(beanFactory, type));
  }

  /**
   * @param fallack bean creation fallback when {@link BeansException} occurs
   */
  public static <T> Supplier<T> beanSupplier(ListableBeanFactory beanFactory, Class<T> type,
      Function<? super Throwable, T> fallack) {

    return SingletonSupplier
        .with(() -> Try(
            () -> beanOfTypeIncludingAncestors(beanFactory, type)).recover(fallack).get());
  }

  public static <T> Supplier<Iterable<T>> beansSupplier(ListableBeanFactory beanFactory,
      Class<T> type) {
    return SingletonSupplier
        .with(() -> beansOfTypeIncludingAncestors(beanFactory, type).values());
  }
}
