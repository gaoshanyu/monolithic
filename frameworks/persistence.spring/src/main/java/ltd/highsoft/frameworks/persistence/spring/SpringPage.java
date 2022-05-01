package ltd.highsoft.frameworks.persistence.spring;

import ltd.highsoft.frameworks.domain.core.Page;

import java.util.List;
import java.util.function.Function;

public class SpringPage<T> implements Page<T> {

    private final org.springframework.data.domain.Page<T> impl;

    public static <T> SpringPage<T> from(org.springframework.data.domain.Page<T> page) {
        return new SpringPage<>(page);
    }

    protected SpringPage(org.springframework.data.domain.Page<T> impl) {
        this.impl = impl;
    }

    @Override
    public List<T> content() {
        return impl.getContent();
    }

    @Override
    public int size() {
        return impl.getSize();
    }

    @Override
    public int number() {
        return impl.getNumber();
    }

    @Override
    public int numberOfElements() {
        return impl.getNumberOfElements();
    }

    @Override
    public int numberOfTotalPages() {
        return impl.getTotalPages();
    }

    @Override
    public long numberOfTotalElements() {
        return impl.getTotalElements();
    }

    @Override
    public boolean first() {
        return impl.isFirst();
    }

    @Override
    public boolean last() {
        return impl.isLast();
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new SpringPage<>(impl.map(converter));
    }

}
