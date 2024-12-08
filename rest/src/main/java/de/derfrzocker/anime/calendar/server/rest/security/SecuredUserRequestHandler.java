package de.derfrzocker.anime.calendar.server.rest.security;

import de.derfrzocker.anime.calendar.server.core.api.user.UserService;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.rest.UserSecurityProvider;
import de.derfrzocker.anime.calendar.server.rest.handler.user.UserRequestHandler;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserCreateResponse;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@RequestScoped
public class SecuredUserRequestHandler {

    @Inject
    UserService userService;
    @Inject
    UserSecurityProvider securityProvider;
    @Inject
    UserRequestHandler userRequestHandler;

    public UserResponse getById(UserId id) {
        ensureAccessToUser(id);

        return this.userRequestHandler.getById(id, this.securityProvider.createRequestContext());
    }

    public UserCreateResponse create() {
        return this.userRequestHandler.create();
    }

    private void ensureAccessToUser(UserId id) {
        Optional<User> user = this.userService.getById(id, this.securityProvider.createSecurityContext());

        if (user.isEmpty()) {
            throw ResourceNotFoundException.with(id).get();
        }

        if (!this.securityProvider.hasAccessToUserData(user.get().id())) {
            throw ResourceNotFoundException.with(id).get();
        }
    }
}
