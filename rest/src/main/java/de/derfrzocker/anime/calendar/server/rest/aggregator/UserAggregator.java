package de.derfrzocker.anime.calendar.server.rest.aggregator;

import de.derfrzocker.anime.calendar.server.core.api.user.UserService;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UserNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.user.CreatedUserHolder;
import de.derfrzocker.anime.calendar.server.rest.mapper.domain.Domain;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserCreateResponse;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserResponse;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

@RequestScoped
public class UserAggregator {

    private static final String USER_ID_ROLE_PREFIX = "User_";
    private static final String NOT_ALLOWED = "Cannot access data for user with id '%s'.";

    @Inject
    UserService userService;
    @Context
    SecurityContext securityContext;

    public UserResponse getById(UserId id) throws UserNotFoundException {
        checkCorrectUser(id);

        return userService.getById(id)
                          .map(Domain::toTransfer)
                          .map(UserResponse::new)
                          .orElseThrow(UserNotFoundException.withId(id));
    }

    public UserCreateResponse create() {
        CreatedUserHolder createdUserHolder = userService.createUser();

        return new UserCreateResponse(createdUserHolder.token(), Domain.toTransfer(createdUserHolder.user()));
    }

    private void checkCorrectUser(UserId id) {
        if (!securityContext.isUserInRole(USER_ID_ROLE_PREFIX + id.raw())) {
            throw new UnauthorizedException(NOT_ALLOWED.formatted(id.raw()));
        }
    }
}
