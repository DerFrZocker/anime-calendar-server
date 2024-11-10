package de.derfrzocker.anime.calendar.server.core.impl.user;

import de.derfrzocker.anime.calendar.server.core.api.user.UserDao;
import de.derfrzocker.anime.calendar.server.core.api.user.UserService;
import de.derfrzocker.anime.calendar.server.core.impl.util.StringGenerator;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.event.user.PostUserCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.user.PreUserCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UserNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.user.CreatedUserHolder;
import de.derfrzocker.anime.calendar.server.model.domain.user.HashedUserToken;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserChangeData;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserToken;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;

@RequestScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserDao userDao;
    @Inject
    Event<PreUserCreateEvent> preUserCreateEventBus;
    @Inject
    Event<PostUserCreateEvent> postUserCreateEventBus;

    @Override
    public Optional<User> getById(UserId id) {
        return userDao.getById(id);
    }

    @Override
    public CreatedUserHolder create() {
        UserId id = createNewUserId();
        UserToken userToken = createNewUserToken(id);
        HashedUserToken hashedUserToken = hashUserToken(userToken);

        UserCreateData userCreateData = new UserCreateData(id, hashedUserToken);

        preUserCreateEventBus.fire(new PreUserCreateEvent(userCreateData));

        User user = userDao.createWithData(new UserCreateData(id, hashedUserToken));

        postUserCreateEventBus.fire(new PostUserCreateEvent(user, userCreateData));

        return new CreatedUserHolder(userToken, user);
    }

    @Override
    public boolean isValidToken(UserToken token) {
        return getById(token.userId()).map(User::hashedToken)
                                      .filter(hashed -> hashUserToken(token).equals(hashed))
                                      .isPresent();
    }

    @Override
    public User updateWithChangeData(UserId id, UserChangeData userChangeData) throws UserNotFoundException {
        return getById(id).map(user -> userDao.updateWithChangeData(user, userChangeData))
                          .orElseThrow(UserNotFoundException.withId(id));
    }

    private UserId createNewUserId() {
        UserId userId;
        do {
            userId = StringGenerator.generateUserId();
        } while (getById(userId).isPresent());

        return userId;
    }

    private UserToken createNewUserToken(UserId id) {
        return StringGenerator.generateUserToken(id);
    }

    private HashedUserToken hashUserToken(UserToken token) {
        return new HashedUserToken(DigestUtils.sha512Hex(token.raw()));
    }
}
