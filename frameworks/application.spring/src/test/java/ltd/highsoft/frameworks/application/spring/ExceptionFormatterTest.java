package ltd.highsoft.frameworks.application.spring;

import ltd.highsoft.frameworks.application.core.ApplicationException;
import ltd.highsoft.frameworks.domain.core.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@MockitoSettings
class ExceptionFormatterTest {

    private @Mock MessageResolver messageResolver;

    @Test
    void should_be_able_to_format_application_exceptions() {
        given(messageResolver.resolve("test", 1, 2)).willReturn("message from resolver");
        ExceptionFormatter formatter = new ExceptionFormatter(messageResolver);
        assertThat(formatter.format(new ApplicationException(new DomainException("test", 1, 2)))).isEqualTo("message from resolver");
    }

    @Test
    void should_be_able_to_format_non_application_exceptions() {
        given(messageResolver.resolve("test")).willReturn("message from resolver");
        ExceptionFormatter formatter = new ExceptionFormatter(messageResolver);
        assertThat(formatter.format(new ApplicationException("test"))).isEqualTo("message from resolver");
    }

}
