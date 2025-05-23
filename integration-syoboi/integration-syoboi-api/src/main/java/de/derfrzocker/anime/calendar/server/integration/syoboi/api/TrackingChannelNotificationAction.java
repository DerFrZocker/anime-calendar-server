package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public record TrackingChannelNotificationAction(NotificationActionId id, Instant createdAt, UserId createdBy,
                                                Instant updatedAt, UserId updatedBy, TID tid, String title,
                                                ChannelId channelId, String channelName) implements ModificationInfo {

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
                                                     createData.channelId(),
                                                     createData.channelName());
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
                                                     channelId(),
                                                     channelName());
    }
}
