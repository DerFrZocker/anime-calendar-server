package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationType;
import org.jspecify.annotations.Nullable;

public record TrackingChannelNotification(
        NotificationId id,
        TID tid,
        String title,
        @Nullable ChannelId currentChannelId,
        @Nullable String currentChannelName) {

    public static final String NOTIFICATION_TYPE_RAW = "TrackingChannel";
    public static final NotificationType NOTIFICATION_TYPE = new NotificationType(NOTIFICATION_TYPE_RAW);

    public static TrackingChannelNotification from(
            NotificationId id,
            TrackingChannelNotificationCreateData createData,
            RequestContext context) {

        return new TrackingChannelNotification(
                id,
                createData.tid(),
                createData.title(),
                createData.currentChannelId(),
                createData.currentChannelName());
    }
}
