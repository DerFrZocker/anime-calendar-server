package de.derfrzocker.anime.calendar.server.rest.resource.user;

import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserCreateResponse;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserResponse;
import de.derfrzocker.anime.calendar.server.rest.security.SecuredUserRequestHandler;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("v3/users")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserResource {

    @Inject
    SecuredUserRequestHandler requestHandler;

    @GET
    @Path("{id}")
    @PermitAll
    public UserResponse getById(@PathParam("id") UserId id) {
        return this.requestHandler.getById(id);
    }

    @POST
    @PermitAll
    public UserCreateResponse create() {
        return this.requestHandler.create();
    }
}
