package de.derfrzocker.anime.calendar.server.rest.security;

import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionType;
import de.derfrzocker.anime.calendar.server.rest.UserSecurityProvider;
import de.derfrzocker.anime.calendar.server.rest.handler.user.UserRequestHandler;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserCreateResponse;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class SecuredUserRequestHandler {

    @Inject
    UserSecurityProvider securityProvider;
    @Inject
    UserRequestHandler userRequestHandler;

    public UserResponse getById(UserId id) {
        ensureAccess(id, PermissionType.READ);

        return this.userRequestHandler.getById(id, this.securityProvider.createRequestContext());
    }

    public UserCreateResponse create() {
        return this.userRequestHandler.create();
    }

    private void ensureAccess(UserId id, PermissionType type) {
        if (!this.securityProvider.hasPermission(id, type, this.securityProvider.createRequestContext())) {
            throw ResourceNotFoundException.with(id).get();
        }
    }
}
