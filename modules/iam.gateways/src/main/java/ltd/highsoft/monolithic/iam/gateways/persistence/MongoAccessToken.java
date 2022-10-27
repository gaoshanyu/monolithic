package ltd.highsoft.monolithic.iam.gateways.persistence;

import lombok.*;
import ltd.highsoft.frameworks.domain.core.Identity;
import ltd.highsoft.frameworks.security.core.GrantedAuthorities;
import ltd.highsoft.monolithic.iam.domain.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

@Document(collection = "access_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MongoAccessToken {

    private @Id String id;
    private @Field(name = "user_account_id") String userAccountId;
    private @Field(name = "user_account_name") String userAccountName;
    private @Field(name = "user_id") String userId;
    private @Field(name = "user_name") String userName;
    private @Field(name = "tenant_id") String tenantId;
    private @Field(name = "tenant_name") String tenantName;
    private @Field(name = "granted_authorities") Set<String> grantedAuthorities;

    @SuppressWarnings("unchecked")
    public MongoAccessToken(AccessToken accessToken) {
        Map<String, Object> descriptionMap = accessToken.description().toMap();
        this.id = (String) descriptionMap.get("id");
        this.userAccountId = (String) descriptionMap.get("owner.userAccount.id");
        this.userAccountName = (String) descriptionMap.get("owner.userAccount.name");
        this.userId = (String) descriptionMap.get("owner.user.id");
        this.userName = (String) descriptionMap.get("owner.user.name");
        this.tenantId = (String) descriptionMap.get("owner.tenant.id");
        this.tenantName = (String) descriptionMap.get("owner.tenant.name");
        this.grantedAuthorities = (Set<String>) descriptionMap.get("authorities");
    }

    public AccessToken asDomain() {
        return AccessToken.restore(
            id, new AccessTokenOwner(new Identity(userAccountId, userAccountName), new Identity(userId, userName), new Identity(tenantId, tenantName)),
            GrantedAuthorities.of(grantedAuthorities)
        );
    }

}
