package com.neo.exp.config;

import io.quarkus.security.credential.TokenCredential;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Provider
public class TokenFilter implements ContainerRequestFilter {

    @Inject
    SecurityIdentity identity;

    @Inject
    UserHolder userHolder;

    @Inject
    JsonWebToken jwt;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        TokenCredential credential = identity.getCredential(TokenCredential.class);
        if (credential != null) {
            userHolder.setToken(credential.getToken());
            userHolder.setUsername(jwt.getName());
            userHolder.setName(jwt.getName());



            String name=jwt.getClaim("name");
            if (name != null) {
                userHolder.setName(name);
            }
            String email=jwt.getClaim("email");
            if (email != null) {
                userHolder.setEmail(email);
            }

            String lastName = jwt.getClaim("family_name");
            if (lastName != null) {
                userHolder.setLastName(lastName);
            }
            String userId = jwt.getClaim("sid");
            if (userId != null) {
                userHolder.setUserId(userId);
            }

            Set<String> roles=jwt.getClaim("roles");
            if (roles!= null) {
                userHolder.setRoles(roles);
            }



        }

    }


}
