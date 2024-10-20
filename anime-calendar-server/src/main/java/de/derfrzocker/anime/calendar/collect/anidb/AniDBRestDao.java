package de.derfrzocker.anime.calendar.collect.anidb;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import java.io.File;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api")
@RegisterRestClient(configKey = "anidb-name-resolver")
@RegisterClientHeaders(ResolverHeaderFactory.class)
public interface AniDBRestDao {

    @GET
    @Path("/anime-titles.xml.gz")
    File getAnimeNames();
}
