package de.derfrzocker.anime.calendar.server.integration.notify.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotification;
import java.util.Optional;
import java.util.stream.Stream;

public interface StreamingNotificationDao {

    Stream<StreamingNotification> getAll(RequestContext context);

    Optional<StreamingNotification> getById(NotificationId id, RequestContext context);

    void create(StreamingNotification notification, RequestContext context);

    void update(StreamingNotification notification, RequestContext context);

    void delete(StreamingNotification notification, RequestContext context);
}
