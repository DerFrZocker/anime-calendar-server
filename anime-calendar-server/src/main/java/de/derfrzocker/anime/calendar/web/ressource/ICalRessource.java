package de.derfrzocker.anime.calendar.web.ressource;

import de.derfrzocker.anime.calendar.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.api.integration.IntegrationService;
import de.derfrzocker.anime.calendar.api.user.UserService;
import de.derfrzocker.anime.calendar.integration.Integrations;
import de.derfrzocker.anime.calendar.server.model.core.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.core.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.IntegrationUserId;
import de.derfrzocker.anime.calendar.server.model.domain.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.Region;
import de.derfrzocker.anime.calendar.web.constrain.ValidateCalendarKey;
import de.derfrzocker.anime.calendar.web.constrain.ValidateMyAnimeListUsername;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.Set;

@Path("ical")
@RequestScoped
@DenyAll
public class ICalRessource {

    @Inject
    UserService userService;

    @Inject
    CalendarService calendarService;

    @Inject
    IntegrationService integrationService;

    @GET
    @Path("myanimelist/{userId}")
    @Produces("text/calendar")
    @PermitAll
    public Response getMyAnimeList(@ValidateMyAnimeListUsername @PathParam("userId") IntegrationUserId userId) {
        return Response.ok(integrationService.getCalendar(Integrations.MY_ANIME_LIST_ID, userId).toString()).build();
    }

    @GET
    @Path("myanimelist")
    @Produces("text/calendar")
    public Response getMyAnimeList(@QueryParam("ani") Set<IntegrationAnimeId> queryAnime) {
        return Response.ok(integrationService.getCalendar(Integrations.MY_ANIME_LIST_ID, queryAnime).toString()).build();
    }

    @GET
    @Path("{calendarKey}")
    @Produces("text/calendar")
    public Response getPersonal(@ValidateCalendarKey @PathParam("calendarKey") CalendarKey calendarKey) {
        return Response.ok(calendarService.buildCalendar(null, AnimeOptionsBuilder.anAnimeOptions(Region.DE_DE).build()).toString()).build();
    }
}
