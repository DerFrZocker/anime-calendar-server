package de.derfrzocker.anime.calendar.server.mongodb.dao.user;

import de.derfrzocker.anime.calendar.server.core.api.user.UserDao;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.data.UserData;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.domain.UserDomain;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@RequestScoped
public class UserMongoDBDaoImpl implements UserDao {

    @Inject
    UserMongoDBRepository repository;

    @Override
    public Optional<User> getById(UserId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(UserData::toDomain);
    }

    @Override
    public void create(User user, RequestContext context) {
        this.repository.persist(UserDomain.toData(user));
    }

    @Override
    public void update(User user, RequestContext context) {
        this.repository.update(UserDomain.toData(user));
    }

    @Override
    public void delete(User user, RequestContext context) {
        this.repository.deleteById(user.id());
    }
}
