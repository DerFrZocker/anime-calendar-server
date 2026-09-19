package de.derfrzocker.anime.calendar.integration.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationAction;

import java.util.Optional;

public interface ManualLinkNotificationActionDao {

    Optional<ManualLinkNotificationAction> getById(NotificationActionId id, RequestContext context);

    void create(ManualLinkNotificationAction action, RequestContext context);

    void update(ManualLinkNotificationAction action, RequestContext context);
}
