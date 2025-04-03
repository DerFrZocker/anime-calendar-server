package de.derfrzocker.anime.calendar.server.rest.handler.anime;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.rest.mapper.domain.AnimeDomain;
import de.derfrzocker.anime.calendar.server.rest.mapper.transfer.AnimeTransfer;
import de.derfrzocker.anime.calendar.server.rest.request.anime.AnimeCreateRequest;
import de.derfrzocker.anime.calendar.server.rest.request.anime.AnimeUpdateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.anime.AnimeResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class AnimeRequestHandler {

    @Inject
    AnimeService service;

    public AnimeResponse getById(AnimeId id, RequestContext context) {
        return this.service.getById(id, context).map(this::toResponse).orElseThrow(ResourceNotFoundException.with(id));
    }

    public AnimeResponse createWithData(AnimeCreateRequest request, RequestContext context) {
        return toResponse(this.service.createWithData(AnimeTransfer.toDomain(request.anime()), context));
    }

    public AnimeResponse updateWithData(AnimeId id, AnimeUpdateRequest request, RequestContext context) {
        return toResponse(this.service.updateWithData(id, AnimeTransfer.toDomain(request.anime()), context));
    }

    public void deleteById(AnimeId id, RequestContext context) {
        this.service.deleteById(id, context);
    }

    private AnimeResponse toResponse(Anime domain) {
        return new AnimeResponse(AnimeDomain.toTransfer(domain));
    }
}
