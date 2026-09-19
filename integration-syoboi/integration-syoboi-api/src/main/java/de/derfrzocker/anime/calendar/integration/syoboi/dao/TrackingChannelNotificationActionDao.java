package de.derfrzocker.anime.calendar.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TrackingChannelNotificationAction;

import java.util.Optional;

public interface TrackingChannelNotificationActionDao {

    Optional<TrackingChannelNotificationAction> getById(NotificationActionId id, RequestContext context);

    void create(TrackingChannelNotificationAction notificationAction, RequestContext context);

    void update(TrackingChannelNotificationAction notificationAction, RequestContext context);
}
