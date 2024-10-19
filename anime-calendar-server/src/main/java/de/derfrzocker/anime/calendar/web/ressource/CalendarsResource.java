package de.derfrzocker.anime.calendar.web.ressource;

import de.derfrzocker.anime.calendar.server.model.core.CalendarId;
import de.derfrzocker.anime.calendar.server.core.api.user.UserService;
import de.derfrzocker.anime.calendar.web.constrain.ValidateId;
import de.derfrzocker.anime.calendar.web.request.calendar.AnimePutRequest;
import jakarta.annotation.security.DenyAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("calendars")
@RequestScoped
@DenyAll
public class CalendarsResource {

    @Inject
    UserService userService;

    @PUT
    @Path("{calendarId}/animes")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setAnimes(@ValidateId @PathParam("calendarId") CalendarId calendarId, AnimePutRequest animePutRequest) {
        // userService.updateUser(calendarId, animePutRequest.animeIds());

        return Response.noContent().build();
    }
}
