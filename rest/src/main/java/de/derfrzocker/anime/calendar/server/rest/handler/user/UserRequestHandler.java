package de.derfrzocker.anime.calendar.server.rest.handler.user;

import de.derfrzocker.anime.calendar.server.core.api.user.UserService;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.user.CreatedUserHolder;
import de.derfrzocker.anime.calendar.server.rest.mapper.domain.UserDomain;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserCreateResponse;
import de.derfrzocker.anime.calendar.server.rest.response.user.UserResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.time.Instant;

@RequestScoped
public class UserRequestHandler {

    // TODO 2024-12-08: Move to better place
    private static final UserId USER_CREATE_SERVICE = new UserId("UCREATESER");

    @Inject
    UserService userService;

    public UserResponse getById(UserId id, RequestContext context) {
        return this.userService.getById(id, context)
                               .map(UserDomain::toTransfer)
                               .map(UserResponse::new)
                               .orElseThrow(ResourceNotFoundException.with(id));
    }

    public UserCreateResponse create() {
        CreatedUserHolder createdUserHolder = this.userService.create(new RequestContext(USER_CREATE_SERVICE,
                                                                                         Instant.now()));

        return new UserCreateResponse(createdUserHolder.token(), UserDomain.toTransfer(createdUserHolder.user()));
    }
}
