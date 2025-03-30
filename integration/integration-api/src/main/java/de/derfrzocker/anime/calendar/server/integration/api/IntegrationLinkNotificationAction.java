package de.derfrzocker.anime.calendar.server.integration.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public record IntegrationLinkNotificationAction(NotificationActionId id, Instant createdAt, UserId createdBy,
                                                Instant updatedAt, UserId updatedBy, AnimeId animeId,
                                                IntegrationId integrationId, IntegrationAnimeId integrationAnimeId,
                                                int score, String bestName) implements ModificationInfo {

    public static IntegrationLinkNotificationAction from(NotificationActionId id,
                                                         IntegrationLinkNotificationActionCreateData createData,
                                                         RequestContext context) {
        return new IntegrationLinkNotificationAction(id,
                                                     context.requestTime(),
                                                     context.requestUser(),
                                                     context.requestTime(),
                                                     context.requestUser(),
                                                     createData.animeId(),
                                                     createData.integrationId(),
                                                     createData.integrationAnimeId(),
                                                     createData.score(),
                                                     createData.bestName());
    }

    public IntegrationLinkNotificationAction updateWithData(IntegrationLinkNotificationActionUpdateData updateData,
                                                            RequestContext context) {
        return new IntegrationLinkNotificationAction(id(),
                                                     createdAt(),
                                                     createdBy(),
                                                     context.requestTime(),
                                                     context.requestUser(),
                                                     animeId(),
                                                     integrationId(),
                                                     integrationAnimeId(),
                                                     score(),
                                                     bestName());
    }
}
