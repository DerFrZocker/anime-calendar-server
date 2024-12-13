package de.derfrzocker.anime.calendar.server.mongodb.dao.user;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.mongodb.data.user.UserDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMongoDBRepository implements PanacheMongoRepositoryBase<UserDO, UserId> {

}
