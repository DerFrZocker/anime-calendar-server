package de.derfrzocker.anime.calendar.server.notify.mongodb.dao;

import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.notify.mongodb.data.NotificationDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotificationMongoDBRepository implements PanacheMongoRepositoryBase<NotificationDO, NotificationId> {

}
