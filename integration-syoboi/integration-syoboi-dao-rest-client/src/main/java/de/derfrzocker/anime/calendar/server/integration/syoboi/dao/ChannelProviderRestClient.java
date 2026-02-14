package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.server.integration.syoboi.data.ChLookupResponseTDO;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/db.php")
@RegisterRestClient(configKey = "syoboi")
public interface ChannelProviderRestClient {

    @GET
    @ClientQueryParam(name = "Command", value = "ChLookup")
    @Produces(MediaType.TEXT_XML)
    ChLookupResponseTDO getChannel(@QueryParam("ChID") String channelId);
}
