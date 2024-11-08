package de.derfrzocker.anime.calendar.server.core.impl.user;

import de.derfrzocker.anime.calendar.server.core.api.user.UserDao;
import de.derfrzocker.anime.calendar.server.core.api.user.UserService;
import de.derfrzocker.anime.calendar.server.core.impl.util.StringGenerator;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.CreatedUserHolder;
import de.derfrzocker.anime.calendar.server.model.domain.user.HashedUserToken;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserToken;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;

@RequestScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserDao userDao;

    @Override
    public Optional<User> getById(UserId id) {
        return userDao.getById(id);
    }

    @Override
    public CreatedUserHolder createUser() {
        UserId id = createNewUserId();
        UserToken userToken = createNewUserToken(id);
        HashedUserToken hashedUserToken = hashUserToken(userToken);

        User user = userDao.createUser(new UserCreateData(id, hashedUserToken));

        return new CreatedUserHolder(userToken, user);
    }

    @Override
    public boolean isValidToken(UserToken token) {
        return getById(token.userId()).map(User::hashedUserToken)
                                      .filter(hashed -> hashUserToken(token).equals(hashed))
                                      .isPresent();
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
