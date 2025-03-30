package de.derfrzocker.anime.calendar.server.core.api.user;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import java.util.Optional;

public interface UserDao {

    Optional<User> getById(UserId id, RequestContext context);

    void create(User user, RequestContext context);

    void update(User user, RequestContext context);

    void delete(User user, RequestContext context);
}
