package de.derfrzocker.anime.calendar.server.integration.notify.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionType;
import java.time.Instant;
import org.jetbrains.annotations.Nullable;

public record StreamingNotificationAction(NotificationActionId id, Instant createdAt, UserId createdBy,
                                          Instant updatedAt, UserId updatedBy, AnimeId animeId, int orgEpisodeIndex,
                                          @Nullable IntegrationId integrationId,
                                          @Nullable IntegrationAnimeId integrationAnimeId, int streamingEpisode,
                                          @Nullable Instant streamingTime) implements ModificationInfo {

    public static final String NOTIFICATION_ACTION_TYPE_RAW = "Streaming";
    public static final NotificationActionType NOTIFICATION_ACTION_TYPE = new NotificationActionType(
            NOTIFICATION_ACTION_TYPE_RAW);

    public static StreamingNotificationAction from(NotificationActionId id,
                                                   StreamingNotificationActionCreateData createData,
                                                   RequestContext context) {
        return new StreamingNotificationAction(id,
                                               context.requestTime(),
                                               context.requestUser(),
                                               context.requestTime(),
                                               context.requestUser(),
                                               createData.animeId(),
                                               createData.orgEpisodeIndex(),
                                               null,
                                               null,
                                               -1,
                                               null);
    }

    public StreamingNotificationAction updateWithData(StreamingNotificationActionUpdateData updateData,
                                                      RequestContext context) {
        return new StreamingNotificationAction(id(),
                                               createdAt(),
                                               createdBy(),
                                               context.requestTime(),
                                               context.requestUser(),
                                               animeId(),
                                               orgEpisodeIndex(),
                                               updateData.integrationId().apply(integrationId()),
                                               updateData.integrationAnimeId().apply(integrationAnimeId()),
                                               updateData.streamingEpisode().apply(streamingEpisode()),
                                               updateData.streamingTime().apply(streamingTime()));
    }
}
