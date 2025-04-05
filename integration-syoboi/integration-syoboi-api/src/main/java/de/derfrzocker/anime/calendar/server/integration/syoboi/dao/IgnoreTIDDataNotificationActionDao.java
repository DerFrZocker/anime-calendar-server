package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import java.util.Optional;
import java.util.stream.Stream;

public interface IgnoreTIDDataNotificationActionDao {

    Stream<IgnoreTIDDataNotificationAction> getAll(RequestContext context);

    Optional<IgnoreTIDDataNotificationAction> getById(NotificationActionId id, RequestContext context);

    void create(IgnoreTIDDataNotificationAction notificationAction, RequestContext context);

    void update(IgnoreTIDDataNotificationAction notificationAction, RequestContext context);

    void delete(IgnoreTIDDataNotificationAction notificationAction, RequestContext context);
}
