package ltd.highsoft.frameworks.application.spring;

import ltd.highsoft.frameworks.security.core.ContextLoader;

import javax.servlet.http.HttpServletRequest;

public class HttpHeaderContextLoader implements AutoCloseable {

    private final ContextLoader contextLoader;

    public HttpHeaderContextLoader(ContextLoader authorizer) {
        this.contextLoader = authorizer;
    }

    public void load(HttpServletRequest request) {
        contextLoader.load(request.getHeader("Authorization"));
    }

    @Override
    public void close() {
        contextLoader.clear();
    }

}
