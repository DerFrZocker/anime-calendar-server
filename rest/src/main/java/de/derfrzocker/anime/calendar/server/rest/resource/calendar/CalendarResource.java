package de.derfrzocker.anime.calendar.server.rest.resource.calendar;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.rest.constrain.ValidateCalendarId;
import de.derfrzocker.anime.calendar.server.rest.request.calendar.CalendarCreateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarResponse;
import de.derfrzocker.anime.calendar.server.rest.security.SecuredCalendarRequestHandler;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("v3/calendars")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CalendarResource {

    @Inject
    SecuredCalendarRequestHandler requestHandler;

    @GET
    @Path("{id}")
    @PermitAll
    public CalendarResponse getById(@ValidateCalendarId @PathParam("id") CalendarId id) {
        return this.requestHandler.getById(id);
    }

    @POST
    @PermitAll
    public CalendarResponse createWithData(@Valid @NotNull CalendarCreateRequest request) {
        return this.requestHandler.createWithData(request);
    }
}
