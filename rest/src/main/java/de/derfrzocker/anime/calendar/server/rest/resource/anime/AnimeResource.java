package de.derfrzocker.anime.calendar.server.rest.resource.anime;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.rest.constrain.ValidateAnimeId;
import de.derfrzocker.anime.calendar.server.rest.request.anime.AnimeCreateRequest;
import de.derfrzocker.anime.calendar.server.rest.request.anime.AnimeUpdateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.anime.AnimeResponse;
import de.derfrzocker.anime.calendar.server.rest.security.SecuredAnimeRequestHandler;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("v3/animes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AnimeResource {

    @Inject
    SecuredAnimeRequestHandler requestHandler;

    @GET
    @Path("{id}")
    @PermitAll
    public AnimeResponse getById(@ValidateAnimeId @PathParam("id") AnimeId id) {
        return this.requestHandler.getById(id);
    }

    @POST
    @PermitAll
    public AnimeResponse createWithData(AnimeCreateRequest request) {
        return this.requestHandler.createWithData(request);
    }

    @PUT
    @Path("{id}")
    @PermitAll
    public AnimeResponse updateWithData(@ValidateAnimeId @PathParam("id") AnimeId id, AnimeUpdateRequest request) {
        return this.requestHandler.updateWithData(id, request);
    }

    @DELETE
    @Path("{id}")
    @PermitAll
    public void deleteById(@ValidateAnimeId @PathParam("id") AnimeId id) {
        this.requestHandler.deleteById(id);
    }
}
