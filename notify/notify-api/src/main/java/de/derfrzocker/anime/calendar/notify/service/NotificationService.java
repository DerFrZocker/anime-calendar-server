package de.derfrzocker.anime.calendar.notify.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.notify.api.Notification;
import de.derfrzocker.anime.calendar.notify.api.NotificationCreateData;
import de.derfrzocker.anime.calendar.notify.api.NotificationUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface NotificationService {

    Stream<Notification> getAll(RequestContext context);

    Optional<Notification> getById(NotificationId id, RequestContext context);

    Notification createWithData(NotificationCreateData createData, RequestContext context);

    Notification updateWithData(NotificationId id, NotificationUpdateData updateData, RequestContext context);

    void deleteById(NotificationId id, RequestContext context);
}
