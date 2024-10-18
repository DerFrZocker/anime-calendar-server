package de.derfrzocker.anime.calendar.web.ressource;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.web.request.anime.AnimePostRequest;
import de.derfrzocker.anime.calendar.web.to.anime.AnimeTo;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("animes")
@PermitAll
public class AnimesResource {

    @Inject
    @ConfigProperty(name = "anime-calendar.web.animes.modify-key")
    Optional<String> modifyKey;

    @Inject
    AnimeService animeService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAnime(AnimePostRequest animePostRequest, @HeaderParam("Authorization") String authorization) {
        if (modifyKey.isEmpty() || modifyKey.get().isEmpty() || modifyKey.get().length() < 128) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }

        if (!modifyKey.get().equals(authorization)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Anime anime = animeService.createAnime(animePostRequest);

        return Response.ok(new AnimeTo(anime.animeId().id())).build();
    }
}
