package de.derfrzocker.anime.calendar.web.ressource;

import de.derfrzocker.anime.calendar.integration.Integrations;
import de.derfrzocker.anime.calendar.server.core.api.integration.IntegrationService;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationUserId;
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

@Path("v3/ical")
@RequestScoped
@DenyAll
public class ICalRessource {

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
        return Response.ok(integrationService.getCalendar(Integrations.MY_ANIME_LIST_ID, queryAnime).toString())
                       .build();
    }
}
