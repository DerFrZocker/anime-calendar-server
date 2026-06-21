package de.derfrzocker.anime.calendar.server.integration.syoboi.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotification;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationCreateData;
import java.util.Optional;

public interface TrackingChannelNotificationService {

    Optional<TrackingChannelNotification> getById(NotificationId id, RequestContext context);

    TrackingChannelNotification createWithData(
            NotificationId id,
            TrackingChannelNotificationCreateData createData,
            RequestContext context);
}
