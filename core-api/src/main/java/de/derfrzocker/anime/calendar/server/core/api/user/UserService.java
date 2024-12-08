package de.derfrzocker.anime.calendar.server.core.api.user;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.CreatedUserHolder;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserChangeData;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserToken;
import java.util.Optional;

public interface UserService {

    Optional<User> getById(UserId id);

    CreatedUserHolder create();

    boolean isValidToken(UserToken token);

    User updateWithChangeData(UserId id, UserChangeData userChangeData);
}
