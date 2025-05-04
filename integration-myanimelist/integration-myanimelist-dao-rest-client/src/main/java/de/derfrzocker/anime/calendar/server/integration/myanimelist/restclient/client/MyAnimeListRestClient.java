package de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.client;

import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.AnimeListResponseTDO;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.MyAnimeListSeason;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.MyAnimeListStatus;
import de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data.UserListResponseTDO;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/v2")
@RegisterRestClient(configKey = "integration-myanimelist-dao")
@RegisterClientHeaders(MyAnimeListHeaderFactory.class)
public interface MyAnimeListRestClient {

    @GET
    @Path("/anime/season/{year}/{season}")
    RestResponse<AnimeListResponseTDO> getSeasonAnime(@PathParam("year") int year,
                                                      @PathParam("season") MyAnimeListSeason season,
                                                      @QueryParam("limit") int limit);

    @GET
    @Path("/users/{userName}/animelist")
    @ClientQueryParam(name = "nsfw", value = "true")
    RestResponse<UserListResponseTDO> getUserAnimes(@PathParam("userName") String userName,
                                                    @QueryParam("status") MyAnimeListStatus status,
                                                    @QueryParam("limit") int limit);
}
