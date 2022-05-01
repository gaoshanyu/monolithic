package ltd.highsoft.frameworks.domain.core;

import java.util.List;
import java.util.function.Function;

public interface Page<T> {

    List<T> content();

    int size();

    int number();

    int numberOfElements();

    int numberOfTotalPages();

    long numberOfTotalElements();

    <U> Page<U> map(Function<? super T, ? extends U> converter);

}
