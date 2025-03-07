package de.derfrzocker.anime.calendar.server.notify.mongodb.dao;

import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.notify.mongodb.data.NotificationActionDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotificationActionMongoDBRepository implements PanacheMongoRepositoryBase<NotificationActionDO, NotificationActionId> {

}
