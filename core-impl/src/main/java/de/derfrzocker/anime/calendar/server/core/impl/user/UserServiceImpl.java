package de.derfrzocker.anime.calendar.server.core.impl.user;

import de.derfrzocker.anime.calendar.server.core.api.user.UserDao;
import de.derfrzocker.anime.calendar.server.core.api.user.UserService;
import de.derfrzocker.anime.calendar.server.core.impl.calendar.UserEventPublisher;
import de.derfrzocker.anime.calendar.server.core.impl.util.StringGenerator;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.user.CreatedUserHolder;
import de.derfrzocker.anime.calendar.server.model.domain.user.HashedUserToken;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserToken;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserUpdateData;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;

@RequestScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserDao dao;
    @Inject
    UserEventPublisher eventPublisher;

    @Override
    public Optional<User> getById(UserId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public CreatedUserHolder create(RequestContext context) {
        UserId id = createNewUserId(context);
        UserToken userToken = createNewUserToken(id);
        HashedUserToken hashedUserToken = hashUserToken(userToken);

        User user = User.from(id, hashedUserToken, context);

        this.eventPublisher.firePreCreateEvent(user, context);
        this.dao.create(user, context);
        this.eventPublisher.firePostCreateEvent(user, context);

        return new CreatedUserHolder(userToken, user);
    }

    @Override
    public User updateWithData(UserId id, UserUpdateData updateData, RequestContext context) {
        User current = getById(id, context).orElseThrow(ResourceNotFoundException.with(id));
        User updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdateEvent(id, updateData, current, updated, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdateEvent(id, updateData, current, updated, context);

        return updated;
    }

    @Override
    public boolean isValidToken(UserToken token, RequestContext context) {
        return getById(token.userId(), context).map(User::hashedToken)
                                               .filter(hashed -> hashUserToken(token).equals(hashed))
                                               .isPresent();
    }

    private UserId createNewUserId(RequestContext context) {
        UserId userId;
        do {
            userId = StringGenerator.generateUserId();
        } while (getById(userId, context).isPresent());

        return userId;
    }

    private UserToken createNewUserToken(UserId id) {
        return StringGenerator.generateUserToken(id);
    }

    private HashedUserToken hashUserToken(UserToken token) {
        return new HashedUserToken(DigestUtils.sha512Hex(token.raw()));
    }
}
