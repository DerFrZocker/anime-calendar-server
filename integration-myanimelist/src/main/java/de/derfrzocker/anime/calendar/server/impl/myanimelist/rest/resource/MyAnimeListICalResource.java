package de.derfrzocker.anime.calendar.server.impl.myanimelist.rest.resource;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.server.impl.myanimelist.rest.constrain.ValidateMyAnimeListUsername;
import de.derfrzocker.anime.calendar.server.impl.myanimelist.rest.handler.MyAnimeListICalRequestHandler;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import java.util.Set;

@Path("v3/ical")
@Produces("text/calendar")
@RequestScoped
public class MyAnimeListICalResource {

    @Inject
    MyAnimeListICalRequestHandler requestHandler;

    @GET
    @Path("myanimelist")
    // TODO 2024-12-16: PermitAll and validation
    public String getByMyAnimeListId(@QueryParam("ani") Set<IntegrationAnimeId> ids) {
        return this.requestHandler.getByIds(ids);
    }

    @GET
    @Path("myanimelist/{userId}")
    @PermitAll
    public String getByMyAnimeListUser(@ValidateMyAnimeListUsername @PathParam("userId") IntegrationUserId userId) {
        return this.requestHandler.getByUser(userId);
    }
}
