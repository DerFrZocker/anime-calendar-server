package de.derfrzocker.anime.calendar.server.rest.security;

import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.exception.BadRequestException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.rest.UserSecurityProvider;
import de.derfrzocker.anime.calendar.server.rest.handler.calendar.CalendarRequestHandler;
import de.derfrzocker.anime.calendar.server.rest.request.calendar.CalendarCreateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@RequestScoped
public class SecuredCalendarRequestHandler {

    @Inject
    CalendarService calendarService;
    @Inject
    UserSecurityProvider securityProvider;
    @Inject
    CalendarRequestHandler requestHandler;

    public CalendarResponse getById(CalendarId id) {
        ensureAccessToCalendar(id);

        return this.requestHandler.getById(id, this.securityProvider.createRequestContext());
    }

    public CalendarResponse createWithData(CalendarCreateRequest request) {
        if (!this.securityProvider.hasAccessToUserData(request.calendar().owner())) {
            throw BadRequestException.with("Cannot create Calendar with owner id '%s'.",
                                           request.calendar().owner().raw()).get();
        }

        return this.requestHandler.createWithData(request, this.securityProvider.createRequestContext());
    }

    private void ensureAccessToCalendar(CalendarId id) {
        Optional<Calendar> calendar = this.calendarService.getById(id, this.securityProvider.createSecurityContext());

        if (calendar.isEmpty()) {
            throw ResourceNotFoundException.with(id).get();
        }

        if (!this.securityProvider.hasAccessToUserData(calendar.get().owner())) {
            throw ResourceNotFoundException.with(id).get();
        }
    }
}
