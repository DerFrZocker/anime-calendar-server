package de.derfrzocker.anime.calendar.server.rest.resource;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UnauthenticatedException;
import de.derfrzocker.anime.calendar.server.rest.aggregator.UserAggregator;
import de.derfrzocker.anime.calendar.server.rest.constrain.ValidateUserId;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserCreateResponse;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserResponse;
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
    UserAggregator userAggregator;

    @GET
    @Path("{id}")
    @PermitAll
    public UserResponse getById(@ValidateUserId @PathParam("id") UserId id) throws UnauthenticatedException {
        return userAggregator.getById(id);
    }

    @POST
    @PermitAll
    public UserCreateResponse create() {
        return userAggregator.create();
    }
}
