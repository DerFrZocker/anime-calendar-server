package de.derfrzocker.anime.calendar.server.integration.mongodb.dao;

import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.mongodb.data.ManualLinkNotificationActionDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ManualLinkNotificationActionMongoDBRepository implements PanacheMongoRepositoryBase<ManualLinkNotificationActionDO, NotificationActionId> {

}
