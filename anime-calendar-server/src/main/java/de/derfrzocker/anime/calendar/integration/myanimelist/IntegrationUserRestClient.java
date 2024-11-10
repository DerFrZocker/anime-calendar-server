package de.derfrzocker.anime.calendar.integration.myanimelist;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/v2/users")
@RegisterRestClient(configKey = "my-anime-list-integration")
@RegisterClientHeaders(IntegrationHeaderFactory.class)
public interface IntegrationUserRestClient {

    @GET
    @Path("/{user_name}/animelist")
    IntegrationUserListResponse getAnimeList(@PathParam("user_name") String userName,
                                             @QueryParam("status") Status status,
                                             @QueryParam("nsfw") boolean nsfw);
}
