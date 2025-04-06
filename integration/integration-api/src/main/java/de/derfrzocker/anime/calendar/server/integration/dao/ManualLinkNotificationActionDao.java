package de.derfrzocker.anime.calendar.server.integration.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationAction;
import java.util.Optional;
import java.util.stream.Stream;

public interface ManualLinkNotificationActionDao {

    Stream<ManualLinkNotificationAction> getAll(RequestContext context);

    Optional<ManualLinkNotificationAction> getById(NotificationActionId id, RequestContext context);

    void create(ManualLinkNotificationAction action, RequestContext context);

    void update(ManualLinkNotificationAction action, RequestContext context);

    void delete(ManualLinkNotificationAction action, RequestContext context);
}
