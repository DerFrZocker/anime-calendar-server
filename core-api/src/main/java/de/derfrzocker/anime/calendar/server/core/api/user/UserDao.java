package de.derfrzocker.anime.calendar.server.core.api.user;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserCreateData;
import java.util.Optional;

public interface UserDao {

    Optional<User> getById(UserId id);

    User createUser(UserCreateData userCreateData);
}
