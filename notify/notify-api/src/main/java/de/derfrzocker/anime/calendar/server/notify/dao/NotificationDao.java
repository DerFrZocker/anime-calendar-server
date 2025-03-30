package de.derfrzocker.anime.calendar.server.notify.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import java.util.Optional;
import java.util.stream.Stream;

public interface NotificationDao {

    Stream<Notification> getAll(RequestContext context);

    Optional<Notification> getById(NotificationId id, RequestContext context);

    void create(Notification notification, RequestContext context);

    void update(Notification notification, RequestContext context);

    void delete(Notification notification, RequestContext context);
}
