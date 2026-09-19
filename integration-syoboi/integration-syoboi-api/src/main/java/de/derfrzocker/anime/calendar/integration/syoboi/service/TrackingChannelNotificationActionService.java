package de.derfrzocker.anime.calendar.integration.syoboi.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TrackingChannelNotificationActionCreateData;

import java.util.Optional;

public interface TrackingChannelNotificationActionService {

    Optional<TrackingChannelNotificationAction> getById(NotificationActionId id, RequestContext context);

    TrackingChannelNotificationAction createWithData(NotificationActionId id,
                                                     TrackingChannelNotificationActionCreateData createData,
                                                     RequestContext context);
}
