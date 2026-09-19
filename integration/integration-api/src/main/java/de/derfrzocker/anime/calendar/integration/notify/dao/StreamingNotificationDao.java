package de.derfrzocker.anime.calendar.integration.notify.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotification;

import java.util.Optional;

public interface StreamingNotificationDao {

    Optional<StreamingNotification> getById(NotificationId id, RequestContext context);

    void create(StreamingNotification notification, RequestContext context);
}
