package de.derfrzocker.anime.calendar.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.syoboi.api.IgnoreTIDDataNotificationAction;

import java.util.Optional;

public interface IgnoreTIDDataNotificationActionDao {

    Optional<IgnoreTIDDataNotificationAction> getById(NotificationActionId id, RequestContext context);

    void create(IgnoreTIDDataNotificationAction notificationAction, RequestContext context);

    void update(IgnoreTIDDataNotificationAction notificationAction, RequestContext context);
}
