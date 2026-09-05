package de.derfrzocker.anime.calendar.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.integration.syoboi.data.TrackingChannelNotificationDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TrackingChannelNotificationMongoDBRepository
        implements PanacheMongoRepositoryBase<TrackingChannelNotificationDO, NotificationId> {

}
