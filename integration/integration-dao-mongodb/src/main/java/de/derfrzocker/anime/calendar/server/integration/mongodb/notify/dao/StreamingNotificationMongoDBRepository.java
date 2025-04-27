package de.derfrzocker.anime.calendar.server.integration.mongodb.notify.dao;

import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.integration.mongodb.notify.data.StreamingNotificationDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StreamingNotificationMongoDBRepository implements PanacheMongoRepositoryBase<StreamingNotificationDO, NotificationId> {

}
