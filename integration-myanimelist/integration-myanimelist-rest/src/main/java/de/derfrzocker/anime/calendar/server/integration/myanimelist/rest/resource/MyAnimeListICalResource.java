package de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.resource;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.constrain.ValidateMyAnimeListUsername;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.handler.MyAnimeListICalRequestHandler;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.transfer.AnimeOptionsTO;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
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
    public String getByMyAnimeListId(@QueryParam("ani") Set<IntegrationAnimeId> ids,
                                     @BeanParam @Valid AnimeOptionsTO options) {
        return this.requestHandler.getByIds(ids, options);
    }

    @GET
    @Path("myanimelist/{userId}")
    @PermitAll
    public String getByMyAnimeListUser(@PathParam("userId") @ValidateMyAnimeListUsername IntegrationUserId userId,
                                       @BeanParam @Valid AnimeOptionsTO options) {
        return this.requestHandler.getByUser(userId, options);
    }
}
