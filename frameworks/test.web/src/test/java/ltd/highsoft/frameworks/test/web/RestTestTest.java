package ltd.highsoft.frameworks.test.web;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

public class RestTestTest extends IntegrationTest {

    @Test
    void should_be_able_to_call_simple_get() {
        var response = get("/web-test/simple-get");
        response.statusCode(is(200));
        response.body("name", is("John"));
    }

}
