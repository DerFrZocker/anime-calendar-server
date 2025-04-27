package de.derfrzocker.anime.calendar.server.integration.notify.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationAction;
import java.util.Optional;
import java.util.stream.Stream;

public interface StreamingNotificationActionDao {

    Stream<StreamingNotificationAction> getAll(RequestContext context);

    Optional<StreamingNotificationAction> getById(NotificationActionId id, RequestContext context);

    void create(StreamingNotificationAction action, RequestContext context);

    void update(StreamingNotificationAction action, RequestContext context);

    void delete(StreamingNotificationAction action, RequestContext context);
}
