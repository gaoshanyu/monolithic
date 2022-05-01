package ltd.highsoft.frameworks.persistence.spring;

import ltd.highsoft.frameworks.domain.core.Pagination;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import static org.assertj.core.api.Assertions.assertThat;

class SpringPaginationTest {

    @Test
    void should_be_able_to_carry_page_number() {
        Pagination pagination = SpringPagination.of(PageRequest.of(3, 10));
        assertThat(pagination.pageNumber()).isEqualTo(3);
    }

    @Test
    void should_be_able_to_carry_page_size() {
        Pagination pagination = SpringPagination.of(PageRequest.of(3, 10));
        assertThat(pagination.pageSize()).isEqualTo(10);
    }

    @Test
    void should_be_able_to_carry_sort() {
        Pagination pagination = SpringPagination.of(PageRequest.of(3, 10, Sort.by(Sort.Order.desc("a"), Sort.Order.asc("b"))));
        assertThat(pagination.sort()).isEqualTo(SpringSort.of(Sort.by(Sort.Order.desc("a"), Sort.Order.asc("b"))));
    }

}
