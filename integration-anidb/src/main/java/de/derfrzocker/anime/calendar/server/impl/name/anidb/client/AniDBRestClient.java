package de.derfrzocker.anime.calendar.server.impl.name.anidb.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import java.io.File;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api")
@RegisterRestClient(configKey = "anidb-name-update")
@RegisterClientHeaders(AniDBHeaderFactory.class)
public interface AniDBRestClient {

    @GET
    @Path("/anime-titles.xml.gz")
    File getAnimeNames();
}
