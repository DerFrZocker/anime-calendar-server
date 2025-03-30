package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.data.TrackingChannelNotificationActionDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TrackingChannelNotificationActionMongoDBRepository implements PanacheMongoRepositoryBase<TrackingChannelNotificationActionDO, NotificationActionId> {

}
