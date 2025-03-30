package de.derfrzocker.anime.calendar.server.rest.security;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UnauthorizedException;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionType;
import de.derfrzocker.anime.calendar.server.rest.UserSecurityProvider;
import de.derfrzocker.anime.calendar.server.rest.handler.anime.AnimeRequestHandler;
import de.derfrzocker.anime.calendar.server.rest.request.anime.AnimeCreateRequest;
import de.derfrzocker.anime.calendar.server.rest.request.anime.AnimeUpdateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.anime.AnimeResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class SecuredAnimeRequestHandler {

    @Inject
    UserSecurityProvider securityProvider;
    @Inject
    AnimeRequestHandler animeRequestHandler;

    public AnimeResponse getById(AnimeId id) {
        ensureAccess(id, PermissionType.READ);

        return this.animeRequestHandler.getById(id, this.securityProvider.createRequestContext());
    }

    public AnimeResponse createWithData(AnimeCreateRequest request) {
        ensureAccess(null, PermissionType.CREATE);

        return this.animeRequestHandler.createWithData(request, this.securityProvider.createRequestContext());
    }


    public AnimeResponse updateWithData(AnimeId id, AnimeUpdateRequest request) {
        ensureAccess(id, PermissionType.UPDATE);

        return this.animeRequestHandler.updateWithData(id, request, this.securityProvider.createRequestContext());
    }

    public void deleteById(AnimeId id) {
        ensureAccess(id, PermissionType.DELETE);

        this.animeRequestHandler.deleteById(id, this.securityProvider.createRequestContext());
    }

    private void ensureAccess(AnimeId id, PermissionType type) {
        if (!this.securityProvider.hasPermission(id, type, this.securityProvider.createRequestContext())) {
            if (type == PermissionType.CREATE) {
                throw new UnauthorizedException();
            }

            throw ResourceNotFoundException.with(id).get();
        }
    }
}
