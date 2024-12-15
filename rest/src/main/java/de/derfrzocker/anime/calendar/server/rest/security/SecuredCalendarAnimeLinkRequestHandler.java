package de.derfrzocker.anime.calendar.server.rest.security;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.exception.BadRequestException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionType;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermission;
import de.derfrzocker.anime.calendar.server.rest.UserSecurityProvider;
import de.derfrzocker.anime.calendar.server.rest.handler.calendar.CalendarAnimeLinkRequestHandler;
import de.derfrzocker.anime.calendar.server.rest.request.calendar.CalendarAnimeLinkCreateOrUpdateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarAnimeLinkListResponse;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarAnimeLinkResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class SecuredCalendarAnimeLinkRequestHandler {

    private static final PermissionAction CALENDAR_ANIME_LINK_CREATE_OR_UPDATE_ANIME_ID = new PermissionAction(
            "calendar-anime-link.create-or-update.anime-id");

    @Inject
    CalendarAnimeLinkRequestHandler requestHandler;
    @Inject
    UserSecurityProvider securityProvider;

    public CalendarAnimeLinkListResponse getAllWithId(CalendarId calendarId) {
        ensureAccess(calendarId, PermissionType.READ);

        return this.requestHandler.getAllWithId(calendarId, this.securityProvider.createRequestContext());
    }

    public CalendarAnimeLinkResponse createOrUpdateWithData(CalendarId calendarId,
                                                            AnimeId animeId,
                                                            CalendarAnimeLinkCreateOrUpdateRequest request) {
        ensureAccess(calendarId, PermissionType.CREATE);
        ensureAccess(calendarId, PermissionType.UPDATE);

        if (!this.securityProvider.hasPermission(animeId,
                                                 CALENDAR_ANIME_LINK_CREATE_OR_UPDATE_ANIME_ID,
                                                 this.securityProvider.createRequestContext())) {
            throw BadRequestException.with("Cannot create Calendar Anime Link with anime id '%s'.", animeId.raw())
                                     .get();
        }

        return this.requestHandler.createOrUpdateWithData(calendarId,
                                                          animeId,
                                                          request,
                                                          this.securityProvider.createRequestContext());
    }

    public void deleteById(CalendarId calendarId, AnimeId animeId) {
        ensureAccess(calendarId, PermissionType.DELETE);

        this.requestHandler.deleteById(calendarId, animeId, this.securityProvider.createRequestContext());
    }

    private void ensureAccess(CalendarId id, PermissionType type) {
        if (!this.securityProvider.hasPermission(id,
                                                 type,
                                                 UserPermission::calendarAnimeLink,
                                                 this.securityProvider.createRequestContext())) {
            throw ResourceNotFoundException.with(id).get();
        }
    }
}
