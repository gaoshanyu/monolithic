package ltd.highsoft.frameworks.security.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SecurityContextTest {

    @Test
    void should_be_able_to_represent_anonymous() {
        assertThat(SecurityContext.ANONYMOUS.token()).isEqualTo("anonymous");
        assertThat(SecurityContext.ANONYMOUS.grantedAuthorities()).isEqualTo(GrantedAuthorities.ANONYMOUS);
    }

    @Test
    void should_be_able_to_represent_system() {
        assertThat(SecurityContext.SYSTEM.token()).isEqualTo("system");
        assertThat(SecurityContext.SYSTEM.grantedAuthorities()).isEqualTo(GrantedAuthorities.SYSTEM);
    }

    @Test
    void should_be_able_to_represent_invalid_context() {
        assertThat(SecurityContext.INVALID.valid()).isFalse();
    }

}
