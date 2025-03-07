package de.derfrzocker.anime.calendar.server.integration.mongodb.dao;

import de.derfrzocker.anime.calendar.server.integration.mongodb.data.IntegrationLinkNotificationActionDO;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IntegrationLinkNotificationActionMongoDBRepository implements PanacheMongoRepositoryBase<IntegrationLinkNotificationActionDO, NotificationActionId> {

}
