package de.derfrzocker.anime.calendar.server.integration.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import java.util.Optional;
import java.util.stream.Stream;

public interface IntegrationLinkNotificationActionDao {

    Stream<IntegrationLinkNotificationAction> getAll(RequestContext context);

    Optional<IntegrationLinkNotificationAction> getById(NotificationActionId id, RequestContext context);

    void create(IntegrationLinkNotificationAction notificationAction, RequestContext context);

    void update(IntegrationLinkNotificationAction notificationAction, RequestContext context);

    void delete(IntegrationLinkNotificationAction notificationAction, RequestContext context);
}
