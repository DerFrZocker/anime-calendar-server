package de.derfrzocker.anime.calendar.web.ressource;

import de.derfrzocker.anime.calendar.api.user.User;
import de.derfrzocker.anime.calendar.api.user.UserService;
import de.derfrzocker.anime.calendar.web.to.user.UserTo;
import jakarta.annotation.security.DenyAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("users")
@RequestScoped
@DenyAll
public class UsersResource {

    @Inject
    UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser() {
        User user = userService.createUser();

        return Response.ok(new UserTo(user.userId().id())).build();
    }
}
