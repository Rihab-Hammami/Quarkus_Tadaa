package com.neo.exp.Rest;

import com.neo.exp.config.UserHolder;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserRessource {
    @Inject
    UserHolder userHolder;

    @GET
    @RolesAllowed({"employe"})
    public Response getUserInfo() {
        String username = userHolder.getUsername();
        String email = userHolder.getEmail();
        String lastName = userHolder.getLastName();

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("username", username);
        userInfo.put("email", email);
        userInfo.put("lastName", lastName);

        return Response.ok(userInfo).build();
    }
}
