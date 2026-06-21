package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotification;
import java.util.Optional;

public interface TrackingChannelNotificationDao {

    Optional<TrackingChannelNotification> getById(NotificationId id, RequestContext context);

    void create(TrackingChannelNotification notification, RequestContext context);
}
