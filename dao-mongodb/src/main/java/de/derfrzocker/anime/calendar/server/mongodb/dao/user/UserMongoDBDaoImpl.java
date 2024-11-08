package de.derfrzocker.anime.calendar.server.mongodb.dao.user;

import de.derfrzocker.anime.calendar.server.core.api.user.UserDao;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserCreateData;
import de.derfrzocker.anime.calendar.server.mongodb.data.UserDO;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.data.Data;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.domain.Domain;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.Optional;

@RequestScoped
public class UserMongoDBDaoImpl implements UserDao {

    @Inject
    UserMongoDBRepository repository;

    @Override
    public Optional<User> getById(UserId id) {
        return Optional.ofNullable(repository.findById(id)).map(Data::toDomain);
    }

    @Override
    public User createUser(UserCreateData userCreateData) {
        UserDO userDO = Domain.toData(userCreateData);

        userDO.createdAt = Instant.now();

        repository.persist(userDO);

        return Data.toDomain(userDO);
    }
}
