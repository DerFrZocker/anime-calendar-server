package de.derfrzocker.anime.calendar.server.rest.resource;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.exception.CalendarNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UnauthenticatedException;
import de.derfrzocker.anime.calendar.server.rest.aggregator.CalendarAggregator;
import de.derfrzocker.anime.calendar.server.rest.constrain.ValidateId;
import de.derfrzocker.anime.calendar.server.rest.request.calendar.AnimeOverrideCreateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarCreateResponse;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("v3/calendars")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class CalendarResource {

    @Inject
    CalendarAggregator calendarAggregator;

    @GET
    @Path("{id}")
    @PermitAll
    public CalendarResponse getById(@ValidateId @PathParam("id") CalendarId id) throws
                                                                                CalendarNotFoundException,
                                                                                UnauthenticatedException {
        return calendarAggregator.getById(id);
    }

    @POST
    @PermitAll
    public CalendarCreateResponse create() throws UnauthenticatedException {
        return calendarAggregator.create();
    }

    @POST
    @Path("{id}/animeoverrides")
    @PermitAll
    public CalendarResponse createAnimeOverride(@PathParam("id") CalendarId id,
                                                AnimeOverrideCreateRequest createRequest) throws
                                                                                          CalendarNotFoundException,
                                                                                          UnauthenticatedException {
        return calendarAggregator.createAnimeOverride(id, createRequest);
    }
}
