package ltd.highsoft.frameworks.security.core;

import ltd.highsoft.frameworks.context.core.GlobalUserContextResetter;
import org.apache.commons.lang3.StringUtils;

public class ContextLoader {

    private final ContextProvider contextProvider;

    public ContextLoader(ContextProvider contextProvider) {
        this.contextProvider = contextProvider;
    }

    public void load(String tokenId) {
        clear();
        if (StringUtils.isBlank(tokenId)) return;
        load(contextProvider.get(tokenId).orElse(Context.INVALID));
    }

    private void load(Context context) {
        GlobalUserContextResetter.reset(context.userContext());
        GlobalSecurityContextResetter.reset(context.securityContext());
    }

    public void clear() {
        GlobalUserContextResetter.clear();
        GlobalSecurityContextResetter.clear();
    }

}
