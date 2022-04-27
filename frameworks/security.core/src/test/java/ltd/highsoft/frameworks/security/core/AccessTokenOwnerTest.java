package ltd.highsoft.frameworks.security.core;

import ltd.highsoft.frameworks.domain.core.Identity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccessTokenOwnerTest {

    private final Identity ACCOUNT_OF_JOHN = new Identity("john@highsoft", "John");
    private final Identity JOHN = new Identity("john", "John");
    private final Identity HIGHSOFT = new Identity("highsoft", "Highsoft");

    @Test
    void should_be_able_to_hold_user_account() {
        assertThat(new AccessTokenOwner(ACCOUNT_OF_JOHN, JOHN, HIGHSOFT).userAccount()).isEqualTo(ACCOUNT_OF_JOHN);
    }

    @Test
    void should_be_able_to_hold_user() {
        assertThat(new AccessTokenOwner(ACCOUNT_OF_JOHN, JOHN, HIGHSOFT).user()).isEqualTo(JOHN);
    }

    @Test
    void should_be_able_to_hold_tenant() {
        assertThat(new AccessTokenOwner(ACCOUNT_OF_JOHN, JOHN, HIGHSOFT).tenant()).isEqualTo(HIGHSOFT);
    }

}
