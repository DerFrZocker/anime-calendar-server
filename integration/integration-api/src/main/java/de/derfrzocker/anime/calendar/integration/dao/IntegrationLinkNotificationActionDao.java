package de.derfrzocker.anime.calendar.integration.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.api.IntegrationLinkNotificationAction;

import java.util.Optional;

public interface IntegrationLinkNotificationActionDao {

    Optional<IntegrationLinkNotificationAction> getById(NotificationActionId id, RequestContext context);

    void create(IntegrationLinkNotificationAction notificationAction, RequestContext context);
}
