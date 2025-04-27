package de.derfrzocker.anime.calendar.server.integration.notify.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationType;
import java.time.Instant;

public record StreamingNotification(NotificationId id, Instant createdAt, UserId createdBy, Instant updatedAt,
                                    UserId updatedBy, AnimeId animeId, int orgEpisodeIndex, Instant orgStreamingTime,
                                    IntegrationId referenceIntegrationId,
                                    IntegrationAnimeId referenceIntegrationAnimeId) implements ModificationInfo {

    public static final String NOTIFICATION_TYPE_RAW = "Streaming";
    public static final NotificationType NOTIFICATION_TYPE = new NotificationType(NOTIFICATION_TYPE_RAW);

    public static StreamingNotification from(NotificationId id,
                                             StreamingNotificationCreateData createData,
                                             RequestContext context) {
        return new StreamingNotification(id,
                                         context.requestTime(),
                                         context.requestUser(),
                                         context.requestTime(),
                                         context.requestUser(),
                                         createData.animeId(),
                                         createData.orgEpisodeIndex(),
                                         createData.orgStreamingTime(),
                                         createData.referenceIntegrationId(),
                                         createData.referenceIntegrationAnimeId());
    }

    public StreamingNotification updateWithData(StreamingNotificationUpdateData updateData, RequestContext context) {
        return new StreamingNotification(id(),
                                         createdAt(),
                                         createdBy(),
                                         context.requestTime(),
                                         context.requestUser(),
                                         animeId(),
                                         orgEpisodeIndex(),
                                         orgStreamingTime(),
                                         referenceIntegrationId(),
                                         referenceIntegrationAnimeId());
    }
}
