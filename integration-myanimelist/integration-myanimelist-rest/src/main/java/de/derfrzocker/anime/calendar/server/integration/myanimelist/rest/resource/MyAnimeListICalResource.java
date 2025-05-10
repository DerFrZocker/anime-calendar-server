package de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.resource;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.rest.api.AnimeOptionsQueryParams;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.constrain.ValidateMyAnimeListAnimeId;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.constrain.ValidateMyAnimeListUsername;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.rest.handler.MyAnimeListICalRequestHandler;
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
import java.util.HashSet;
import java.util.Set;

@Path("v3/ical")
@Produces("text/calendar")
@RequestScoped
public class MyAnimeListICalResource {

    @Inject
    MyAnimeListICalRequestHandler requestHandler;

    @GET
    @Path("myanimelist")
    @PermitAll
    public String getByMyAnimeListId(@QueryParam("animeId") Set<@ValidateMyAnimeListAnimeId IntegrationAnimeId> animeIds,
                                     @QueryParam("ani") Set<@ValidateMyAnimeListAnimeId IntegrationAnimeId> ani,
                                     @BeanParam @Valid AnimeOptionsQueryParams options) {
        Set<IntegrationAnimeId> ids = new HashSet<>(animeIds);
        ids.addAll(ani);
        return this.requestHandler.getByIds(ids, options);
    }

    @GET
    @Path("myanimelist/{userId}")
    @PermitAll
    public String getByMyAnimeListUser(@PathParam("userId") @ValidateMyAnimeListUsername IntegrationUserId userId,
                                       @BeanParam @Valid AnimeOptionsQueryParams options) {
        return this.requestHandler.getByUser(userId, options);
    }
}
