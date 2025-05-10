package de.derfrzocker.anime.calendar.server.ical.rest.resource;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.rest.api.AnimeOptionsQueryParams;
import de.derfrzocker.anime.calendar.rest.constrain.ValidateAnimeId;
import de.derfrzocker.anime.calendar.server.ical.rest.constrain.ValidateIntegrationId;
import de.derfrzocker.anime.calendar.server.ical.rest.handler.AnimeIdICalRequestHandler;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import java.util.HashSet;
import java.util.Set;

@Path("v3/ical")
@Produces("text/calendar")
@RequestScoped
public class AnimeIdICalResource {

    @Inject
    AnimeIdICalRequestHandler requestHandler;

    @GET
    @PermitAll
    public String getByIds(@QueryParam("animeId") Set<@ValidateAnimeId AnimeId> animeIds,
                           @QueryParam("ani") Set<@ValidateAnimeId AnimeId> ani,
                           @QueryParam("integrationId") @ValidateIntegrationId IntegrationId integrationId,
                           @BeanParam @Valid AnimeOptionsQueryParams options) {
        Set<AnimeId> ids = new HashSet<>(animeIds);
        ids.addAll(ani);
        return this.requestHandler.getByIds(ids, integrationId, options);
    }
}
