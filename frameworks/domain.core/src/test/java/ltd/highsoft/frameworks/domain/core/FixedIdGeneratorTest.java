package ltd.highsoft.frameworks.domain.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FixedIdGeneratorTest {

    public static final String FIXED = "fixed";

    @Test
    void should_generate_fixed_ids() {
        IdGenerator idGenerator = new FixedIdGenerator(FIXED);
        assertThat(idGenerator.nextId()).isEqualTo(FIXED);
        assertThat(idGenerator.nextReadableId()).isEqualTo(FIXED);
    }

}
