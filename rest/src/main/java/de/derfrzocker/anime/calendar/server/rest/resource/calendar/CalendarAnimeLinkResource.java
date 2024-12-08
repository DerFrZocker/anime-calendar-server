package de.derfrzocker.anime.calendar.server.rest.resource.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UnauthenticatedException;
import de.derfrzocker.anime.calendar.server.rest.constrain.ValidateId;
import de.derfrzocker.anime.calendar.server.rest.request.calendar.CalendarAnimeLinkCreateOrUpdateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarAnimeLinkListResponse;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarAnimeLinkResponse;
import de.derfrzocker.anime.calendar.server.rest.security.SecuredCalendarAnimeLinkRequestHandler;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("v3/calendars")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CalendarAnimeLinkResource {

    @Inject
    SecuredCalendarAnimeLinkRequestHandler requestHandler;

    @GET
    @Path("{id}/animes")
    @PermitAll
    public CalendarAnimeLinkListResponse getAllWithId(@ValidateId @PathParam("id") CalendarId id) throws
                                                                                                  UnauthenticatedException {
        return this.requestHandler.getAllWithId(id);
    }

    @PUT
    @Path("{id}/animes/{animeId}")
    @PermitAll
    public CalendarAnimeLinkResponse createOrUpdateWithData(@ValidateId @PathParam("id") CalendarId id,
                                                            @ValidateId @PathParam("animeId") AnimeId animeId,
                                                            @Valid @NotNull CalendarAnimeLinkCreateOrUpdateRequest request) throws
                                                                                                                            UnauthenticatedException {
        return this.requestHandler.createOrUpdateWithData(id, animeId, request);
    }

    @DELETE
    @Path("{id}/animes/{animeId}")
    @PermitAll
    public void deleteById(@ValidateId @PathParam("id") CalendarId id,
                           @ValidateId @PathParam("animeId") AnimeId animeId) throws UnauthenticatedException {
        this.requestHandler.deleteById(id, animeId);
    }
}
