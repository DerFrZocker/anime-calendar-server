package de.derfrzocker.anime.calendar.external.myanimelist.rest.resource;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalUserId;
import de.derfrzocker.anime.calendar.external.myanimelist.rest.handler.MyAnimeListICalRequestHandler;
import de.derfrzocker.anime.calendar.external.myanimelist.rest.model.EpisodeBuildOptionQueryParam;
import de.derfrzocker.anime.calendar.external.myanimelist.rest.validation.ValidateMyAnimeListAnimeId;
import de.derfrzocker.anime.calendar.external.myanimelist.rest.validation.ValidateMyAnimeListUserId;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
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
class MyAnimeListICalResource {

    private final MyAnimeListICalRequestHandler requestHandler;

    @Inject
    MyAnimeListICalResource(MyAnimeListICalRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @GET
    @Path("myanimelist")
    @PermitAll
    public Uni<String> getByMyAnimeListId(
            @QueryParam("animeId") Set<@ValidateMyAnimeListAnimeId ExternalAnimeId> animeIds,
            @QueryParam("ani") Set<@ValidateMyAnimeListAnimeId ExternalAnimeId> ani,
            @BeanParam @Valid EpisodeBuildOptionQueryParam options) {
        Set<ExternalAnimeId> ids = new HashSet<>(animeIds);
        ids.addAll(ani);
        return this.requestHandler.generateICal(ids, options);
    }

    @GET
    @Path("myanimelist/{userId}")
    @PermitAll
    public Uni<String> getByMyAnimeListUser(
            @PathParam("userId") @ValidateMyAnimeListUserId ExternalUserId userId,
            @BeanParam @Valid EpisodeBuildOptionQueryParam options) {
        return this.requestHandler.generateICal(userId, options);
    }
}
