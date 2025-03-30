package de.derfrzocker.anime.calendar.server.integration.mongodb.dao;

import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.mongodb.data.IntegrationLinkNotificationActionDO;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IntegrationLinkNotificationActionMongoDBRepository implements PanacheMongoRepositoryBase<IntegrationLinkNotificationActionDO, NotificationActionId> {

}
