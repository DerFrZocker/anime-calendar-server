package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import java.util.Optional;
import java.util.stream.Stream;

public interface TrackingChannelNotificationActionDao {

    Stream<TrackingChannelNotificationAction> getAll(RequestContext context);

    Optional<TrackingChannelNotificationAction> getById(NotificationActionId id, RequestContext context);

    void create(TrackingChannelNotificationAction notificationAction, RequestContext context);

    void update(TrackingChannelNotificationAction notificationAction, RequestContext context);

    void delete(TrackingChannelNotificationAction notificationAction, RequestContext context);
}
