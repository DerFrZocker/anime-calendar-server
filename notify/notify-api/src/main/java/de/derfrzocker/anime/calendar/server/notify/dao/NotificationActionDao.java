package de.derfrzocker.anime.calendar.server.notify.dao;

import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import java.util.Optional;
import java.util.stream.Stream;

public interface NotificationActionDao {

    Stream<NotificationAction> getAll(RequestContext context);

    Stream<NotificationAction> getAllWithData(NotificationId notificationId, RequestContext context);

    Optional<NotificationAction> getById(NotificationActionId id, RequestContext context);

    void create(NotificationAction notificationAction, RequestContext context);

    void update(NotificationAction notificationAction, RequestContext context);

    void delete(NotificationAction notificationAction, RequestContext context);
}
