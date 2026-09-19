package de.derfrzocker.anime.calendar.notify.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.notify.api.Notification;
import de.derfrzocker.anime.calendar.notify.api.NotificationCreateData;

import java.util.Optional;

public interface NotificationService {

    Optional<Notification> getById(NotificationId id, RequestContext context);

    Notification createWithData(NotificationCreateData createData, RequestContext context);
}
