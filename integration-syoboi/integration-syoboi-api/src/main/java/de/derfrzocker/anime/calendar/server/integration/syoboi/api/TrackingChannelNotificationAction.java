package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.ModificationInfo;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import java.time.Instant;

public record TrackingChannelNotificationAction(NotificationActionId id, Instant createdAt, UserId createdBy,
                                                Instant updatedAt, UserId updatedBy, TID tid, String title,
                                                Channel channel) implements ModificationInfo {

    public static TrackingChannelNotificationAction from(NotificationActionId id,
                                                         TrackingChannelNotificationActionCreateData createData,
                                                         RequestContext context) {
        return new TrackingChannelNotificationAction(id,
                                                     context.requestTime(),
                                                     context.requestUser(),
                                                     context.requestTime(),
                                                     context.requestUser(),
                                                     createData.tid(),
                                                     createData.title(),
                                                     createData.channel());
    }

    public TrackingChannelNotificationAction updateWithData(TrackingChannelNotificationActionUpdateData updateData,
                                                            RequestContext context) {
        return new TrackingChannelNotificationAction(id(),
                                                     createdAt(),
                                                     createdBy(),
                                                     context.requestTime(),
                                                     context.requestUser(),
                                                     tid(),
                                                     title(),
                                                     channel());
    }
}
