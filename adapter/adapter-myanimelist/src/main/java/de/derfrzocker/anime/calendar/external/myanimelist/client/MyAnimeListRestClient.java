package de.derfrzocker.anime.calendar.external.myanimelist.client;

import de.derfrzocker.anime.calendar.external.myanimelist.model.UserListResponseTDO;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/v2")
@RegisterRestClient(configKey = "adapter-myanimelist")
@RegisterClientHeaders(MyAnimeListHeaderFactory.class)
public interface MyAnimeListRestClient {

    @GET
    @Path("/users/{username}/animelist")
    @ClientQueryParam(name = "nsfw", value = "true")
    Uni<UserListResponseTDO> fetchUserAnimes(
            @PathParam("username") String username,
            @QueryParam("status") String status,
            @QueryParam("limit") int limit);
}
