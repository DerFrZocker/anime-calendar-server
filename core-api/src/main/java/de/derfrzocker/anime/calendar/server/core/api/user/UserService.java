package de.derfrzocker.anime.calendar.server.core.api.user;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.CreatedUserHolder;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserToken;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserUpdateData;
import java.util.Optional;

public interface UserService {

    Optional<User> getById(UserId id, RequestContext context);

    CreatedUserHolder create(RequestContext context);

    User updateWithData(UserId id, UserUpdateData updateData, RequestContext context);

    boolean isValidToken(UserToken token, RequestContext context);
}
