package de.derfrzocker.anime.calendar.server.impl.myanimelist.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/v2")
@RegisterRestClient(configKey = "myanimelist-name-and-season-update")
@RegisterClientHeaders(MyAnimeListHeaderFactory.class)
public interface MyAnimeListRestClient {

    @GET
    @Path("/anime/season/{year}/{season}")
    MyAnimeListAnimeListResponse getSeasonAnime(@PathParam("year") int year,
                                                @PathParam("season") MyAnimeListSeason season,
                                                @QueryParam("limit") int limit);

    @GET
    @Path("/users/{userName}/animelist")
    MyAnimeListUserListResponse getUserAnimes(@PathParam("userName") String userName,
                                              @QueryParam("status") MyAnimeListStatus status,
                                              @QueryParam("nsfw") boolean nsfw);
}
