package de.derfrzocker.anime.calendar.server.mongodb.dao.permission;

import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.mongodb.data.permission.UserPermissionDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserPermissionMongoDBRepository implements PanacheMongoRepositoryBase<UserPermissionDO, UserId> {

}
