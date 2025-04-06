package de.derfrzocker.anime.calendar.server.integration.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;
import org.jetbrains.annotations.Nullable;

public record ManualLinkNotificationAction(NotificationActionId id, Instant createdAt, UserId createdBy,
                                           Instant updatedAt, UserId updatedBy, AnimeId animeId,
                                           IntegrationId integrationId,
                                           @Nullable IntegrationAnimeId integrationAnimeId) implements ModificationInfo {

    public static ManualLinkNotificationAction from(NotificationActionId id,
                                                    ManualLinkNotificationActionCreateData createData,
                                                    RequestContext context) {
        return new ManualLinkNotificationAction(id,
                                                context.requestTime(),
                                                context.requestUser(),
                                                context.requestTime(),
                                                context.requestUser(),
                                                createData.animeId(),
                                                createData.integrationId(),
                                                null);
    }

    public ManualLinkNotificationAction updateWithData(ManualLinkNotificationActionUpdateData updateData,
                                                       RequestContext context) {
        return new ManualLinkNotificationAction(id(),
                                                createdAt(),
                                                createdBy(),
                                                context.requestTime(),
                                                context.requestUser(),
                                                animeId(),
                                                integrationId(),
                                                updateData.integrationAnimeId().apply(integrationAnimeId()));
    }
}
