package de.derfrzocker.anime.calendar.plugin.mongodb.user;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserDODao implements PanacheMongoRepositoryBase<UserDO, UserId> {

}
