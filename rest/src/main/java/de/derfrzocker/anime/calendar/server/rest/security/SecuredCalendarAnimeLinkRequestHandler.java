package de.derfrzocker.anime.calendar.server.rest.security;

import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UnauthenticatedException;
import de.derfrzocker.anime.calendar.server.rest.UserSecurityProvider;
import de.derfrzocker.anime.calendar.server.rest.handler.calendar.CalendarAnimeLinkRequestHandler;
import de.derfrzocker.anime.calendar.server.rest.request.calendar.CalendarAnimeLinkCreateOrUpdateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarAnimeLinkListResponse;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarAnimeLinkResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@RequestScoped
public class SecuredCalendarAnimeLinkRequestHandler {

    @Inject
    CalendarService calendarService;
    @Inject
    CalendarAnimeLinkRequestHandler requestHandler;
    @Inject
    UserSecurityProvider securityProvider;

    public CalendarAnimeLinkListResponse getAllWithId(CalendarId calendarId) throws UnauthenticatedException {
        ensureAccessToCalendar(calendarId);

        return this.requestHandler.getAllWithId(calendarId, this.securityProvider.createRequestContext());
    }

    public CalendarAnimeLinkResponse createOrUpdateWithData(CalendarId calendarId,
                                                            AnimeId animeId,
                                                            CalendarAnimeLinkCreateOrUpdateRequest request) throws
                                                                                                            UnauthenticatedException {
        ensureAccessToCalendar(calendarId);

        return this.requestHandler.createOrUpdateWithData(calendarId,
                                                          animeId,
                                                          request,
                                                          this.securityProvider.createRequestContext());
    }

    public void deleteById(CalendarId calendarId, AnimeId animeId) throws UnauthenticatedException {
        ensureAccessToCalendar(calendarId);

        this.requestHandler.deleteById(calendarId, animeId, this.securityProvider.createRequestContext());
    }

    private void ensureAccessToCalendar(CalendarId id) throws UnauthenticatedException {
        Optional<Calendar> calendar = this.calendarService.getById(id, this.securityProvider.createSecurityContext());

        if (calendar.isEmpty()) {
            throw ResourceNotFoundException.with(id).get();
        }

        this.securityProvider.ensureAccessToUserData(calendar.get().owner());
    }
}
