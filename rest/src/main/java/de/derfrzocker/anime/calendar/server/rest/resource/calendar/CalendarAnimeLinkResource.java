package de.derfrzocker.anime.calendar.server.rest.resource.calendar;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.rest.constrain.ValidateAnimeId;
import de.derfrzocker.anime.calendar.server.rest.constrain.ValidateCalendarId;
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
    public CalendarAnimeLinkListResponse getAllWithId(@ValidateCalendarId @PathParam("id") CalendarId id) {
        return this.requestHandler.getAllWithId(id);
    }

    @PUT
    @Path("{id}/animes/{animeId}")
    @PermitAll
    public CalendarAnimeLinkResponse createOrUpdateWithData(@ValidateCalendarId @PathParam("id") CalendarId id,
                                                            @ValidateAnimeId @PathParam("animeId") AnimeId animeId,
                                                            @Valid @NotNull CalendarAnimeLinkCreateOrUpdateRequest request) {
        return this.requestHandler.createOrUpdateWithData(id, animeId, request);
    }

    @DELETE
    @Path("{id}/animes/{animeId}")
    @PermitAll
    public void deleteById(@ValidateCalendarId @PathParam("id") CalendarId id,
                           @ValidateAnimeId @PathParam("animeId") AnimeId animeId) {
        this.requestHandler.deleteById(id, animeId);
    }
}
