package de.derfrzocker.anime.calendar.server.rest.security;

import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.BadRequestException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UnauthorizedException;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionType;
import de.derfrzocker.anime.calendar.server.rest.UserSecurityProvider;
import de.derfrzocker.anime.calendar.server.rest.handler.calendar.CalendarRequestHandler;
import de.derfrzocker.anime.calendar.server.rest.request.calendar.CalendarCreateRequest;
import de.derfrzocker.anime.calendar.server.rest.response.calendar.CalendarResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class SecuredCalendarRequestHandler {

    private static final PermissionAction CALENDAR_CREATE_OWNER = new PermissionAction("calendar.create.owner");

    @Inject
    UserSecurityProvider securityProvider;
    @Inject
    CalendarRequestHandler requestHandler;

    public CalendarResponse getById(CalendarId id) {
        ensureAccess(id, PermissionType.READ);

        return this.requestHandler.getById(id, this.securityProvider.createRequestContext());
    }

    public CalendarResponse createWithData(CalendarCreateRequest request) {
        ensureAccess(null, PermissionType.CREATE);

        if (!this.securityProvider.hasPermission(request.calendar().owner(),
                                                 CALENDAR_CREATE_OWNER,
                                                 this.securityProvider.createRequestContext())) {
            throw BadRequestException.with("Cannot create Calendar with owner id '%s'.",
                                           request.calendar().owner().raw()).get();
        }

        return this.requestHandler.createWithData(request, this.securityProvider.createRequestContext());
    }

    private void ensureAccess(CalendarId id, PermissionType type) {
        if (!this.securityProvider.hasPermission(id, type, this.securityProvider.createRequestContext())) {
            if (type == PermissionType.CREATE) {
                throw new UnauthorizedException();
            }

            throw ResourceNotFoundException.with(id).get();
        }
    }
}
