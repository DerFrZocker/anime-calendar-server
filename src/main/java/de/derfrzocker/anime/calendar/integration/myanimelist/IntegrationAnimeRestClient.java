package de.derfrzocker.anime.calendar.integration.myanimelist;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/v2/anime")
@RegisterRestClient(configKey = "my-anime-list-integration")
@RegisterClientHeaders(IntegrationHeaderFactory.class)
public interface IntegrationAnimeRestClient {

    @GET
    @Path("/season/{year}/{season}")
    IntegrationAnimeNameListResponse getSeasonAnime(@PathParam("year") int year, @PathParam("season") Season season, @QueryParam("limit") int limit);
}
