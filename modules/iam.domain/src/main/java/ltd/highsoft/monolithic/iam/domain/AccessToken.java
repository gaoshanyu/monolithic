package ltd.highsoft.monolithic.iam.domain;

import ltd.highsoft.frameworks.context.core.UserContext;
import ltd.highsoft.frameworks.domain.core.*;
import ltd.highsoft.frameworks.domain.core.fields.*;
import ltd.highsoft.frameworks.security.core.*;

public final class AccessToken implements Context, Aggregate {

    private final Id id;
    private final AccessTokenOwner owner;
    private final GrantedAuthorities grantedAuthorities;

    public static AccessToken create(AccessTokenOwner owner, GrantedAuthorities authorities) {
        return new AccessToken(owner, authorities);
    }

    public static AccessToken restore(String id, AccessTokenOwner owner, GrantedAuthorities authorities) {
        return new AccessToken(id, owner, authorities);
    }

    private AccessToken(String id, AccessTokenOwner owner, GrantedAuthorities grantedAuthorities) {
        this.id = new Id(id);
        this.owner = owner;
        this.grantedAuthorities = grantedAuthorities;
    }

    private AccessToken(AccessTokenOwner owner, GrantedAuthorities grantedAuthorities) {
        this.id = new Id();
        this.owner = owner;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public void verify() {
        id.verify();
        owner.verify();
    }

    public AccessTokenOwner owner() {
        return owner;
    }

    public String token() {
        return id.get();
    }

    public GrantedAuthorities grantedAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public UserContext userContext() {
        return owner().asUserContext();
    }

    @Override
    public SecurityContext securityContext() {
        return new SimpleSecurityContext(token(), grantedAuthorities());
    }

    public void content(ValueSink sink) {
        sink.put("accessToken", token());
        sink.put("authorities", grantedAuthorities.asSet());
    }

}
