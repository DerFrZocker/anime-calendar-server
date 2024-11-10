package de.derfrzocker.anime.calendar.server.rest.aggregator;

import de.derfrzocker.anime.calendar.server.core.api.user.UserService;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UnauthenticatedException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UserNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.user.CreatedUserHolder;
import de.derfrzocker.anime.calendar.server.rest.UserSecurityProvider;
import de.derfrzocker.anime.calendar.server.rest.mapper.domain.Domain;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserCreateResponse;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class UserAggregator {

    @Inject
    UserService userService;
    @Inject
    UserSecurityProvider userSecurityProvider;

    public UserResponse getById(UserId id) throws UserNotFoundException, UnauthenticatedException {
        userSecurityProvider.ensureAccessToUserData(id);

        return userService.getById(id)
                          .map(Domain::toTransfer)
                          .map(UserResponse::new)
                          .orElseThrow(UserNotFoundException.withId(id));
    }

    public UserCreateResponse create() {
        CreatedUserHolder createdUserHolder = userService.create();

        return new UserCreateResponse(createdUserHolder.token(), Domain.toTransfer(createdUserHolder.user()));
    }
}
