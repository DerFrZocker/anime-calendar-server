package de.derfrzocker.anime.calendar.server.integration.dao;

import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.util.Optional;
import java.util.stream.Stream;

public interface IntegrationLinkNotificationActionDao {

    Stream<IntegrationLinkNotificationAction> getAll(RequestContext context);

    Optional<IntegrationLinkNotificationAction> getById(NotificationActionId id, RequestContext context);

    void create(IntegrationLinkNotificationAction notificationAction, RequestContext context);

    void update(IntegrationLinkNotificationAction notificationAction, RequestContext context);

    void delete(IntegrationLinkNotificationAction notificationAction, RequestContext context);
}
