package de.derfrzocker.anime.calendar.server.integration.mongodb.notify.dao;

import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.mongodb.notify.data.StreamingNotificationActionDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StreamingNotificationActionMongoDBRepository implements PanacheMongoRepositoryBase<StreamingNotificationActionDO, NotificationActionId> {

}
