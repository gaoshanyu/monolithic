package ltd.highsoft.frameworks.test.moco;

import com.github.dreamhead.moco.HttpServer;

import static com.github.dreamhead.moco.Moco.*;
import static ltd.highsoft.frameworks.test.moco.MocoValidation.hit;

public class CustomizedMocoServerConfig implements MocoServerConfig {

    // TODO : Change it to config or random port.
    private static final Integer MOCO_PORT = 9999;

    @Override
    public HttpServer configure() {
        HttpServer server = httpServer(MOCO_PORT, hit());
        server.request(by(uri("/ping"))).response(text("pong"));
        return server;
    }

}
