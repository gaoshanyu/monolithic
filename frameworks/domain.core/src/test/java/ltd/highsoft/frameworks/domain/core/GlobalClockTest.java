package ltd.highsoft.frameworks.domain.core;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalClockTest {

    @Test
    void should_be_able_to_retrieve_time_from_underlying_clock() {
        GlobalClockResetter.fixAt(Instant.parse("2020-01-01T00:00:00Z"));
        assertThat(GlobalClock.now()).isEqualTo(Instant.parse("2020-01-01T00:00:00Z"));
    }

    @Test
    void should_be_able_to_retrieve_local_now_from_underlying_clock() {
        GlobalClockResetter.fixAt(Instant.parse("2019-12-31T16:00:00Z"));
        assertThat(GlobalClock.localNow()).isEqualTo(ZonedDateTime.parse("2020-01-01T00:00:00+08:00[Asia/Shanghai]"));
        assertThat(GlobalClock.zone()).isEqualTo(ZoneId.of("Asia/Shanghai"));
    }

    @Test
    void should_be_able_to_reset_to_real_clock() {
        GlobalClockResetter.fixAt(Instant.parse("2019-12-08T16:00:00Z"));
        GlobalClockResetter.reset();
        assertThat(GlobalClock.now()).isBetween(Instant.now().minus(3, ChronoUnit.SECONDS), Instant.now());
    }

}
