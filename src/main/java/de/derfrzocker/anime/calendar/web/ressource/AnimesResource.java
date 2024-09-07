/*
 * MIT License
 *
 * Copyright (c) 2022 Marvin (DerFrZocker)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
