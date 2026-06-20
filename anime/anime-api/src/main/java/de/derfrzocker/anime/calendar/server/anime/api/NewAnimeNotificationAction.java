package de.derfrzocker.anime.calendar.server.anime.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionType;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public record NewAnimeNotificationAction(
        NotificationActionId id,
        Instant createdAt,
        UserId createdBy,
        Instant updatedAt,
        UserId updatedBy,
        String title,
        int episodeCount,
        int score,
        IntegrationId sourceIntegrationId,
        IntegrationAnimeId sourceIntegrationAnimeId,
        IntegrationId integrationId,
        IntegrationAnimeId integrationAnimeId) implements ModificationInfo {

    public static final String NOTIFICATION_ACTION_TYPE_RAW = "NewAnime";
    public static final NotificationActionType NOTIFICATION_ACTION_TYPE = new NotificationActionType(
            NOTIFICATION_ACTION_TYPE_RAW);

    public static NewAnimeNotificationAction from(
            NotificationActionId id,
            NewAnimeNotificationActionCreateData createData,
            RequestContext context) {

        return new NewAnimeNotificationAction(
                id,
                context.requestTime(),
                context.requestUser(),
                context.requestTime(),
                context.requestUser(),
                createData.title(),
                createData.episodeCount(),
                createData.score(),
                createData.sourceIntegrationId(),
                createData.sourceIntegrationAnimeId(),
                createData.integrationId(),
                createData.integrationAnimeId());
    }

    public NewAnimeNotificationAction updateWithData(
            NewAnimeNotificationActionUpdateData updateData,
            RequestContext context) {

        return new NewAnimeNotificationAction(
                id(),
                createdAt(),
                createdBy(),
                context.requestTime(),
                context.requestUser(),
                updateData.title().apply(title()),
                episodeCount(),
                score(),
                sourceIntegrationId(),
                sourceIntegrationAnimeId(),
                integrationId(),
                integrationAnimeId());
    }
}
