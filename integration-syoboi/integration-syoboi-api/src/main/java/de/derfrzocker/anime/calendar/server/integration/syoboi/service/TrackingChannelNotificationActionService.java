package de.derfrzocker.anime.calendar.server.integration.syoboi.service;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.util.Optional;
import java.util.stream.Stream;

public interface TrackingChannelNotificationActionService {

    Stream<TrackingChannelNotificationAction> getAll(RequestContext context);

    Optional<TrackingChannelNotificationAction> getById(NotificationActionId id, RequestContext context);

    TrackingChannelNotificationAction createWithData(NotificationActionId id,
                                                     TrackingChannelNotificationActionCreateData createData,
                                                     RequestContext context);

    TrackingChannelNotificationAction updateWithData(NotificationActionId id,
                                                     TrackingChannelNotificationActionUpdateData updateData,
                                                     RequestContext context);

    void deleteById(NotificationActionId id, RequestContext context);
}
