package de.derfrzocker.anime.calendar.server.anime.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;
import java.util.Map;

public record NewAnimeNotificationAction(NotificationActionId id, Instant createdAt, UserId createdBy,
                                         Instant updatedAt, UserId updatedBy,
                                         String title, int episodeCount, int score,
                                         Map<IntegrationId, IntegrationAnimeId> links) implements ModificationInfo {

    public static NewAnimeNotificationAction from(NotificationActionId id,
                                                  NewAnimeNotificationActionCreateData createData,
                                                  RequestContext context) {
        return new NewAnimeNotificationAction(id,
                                              context.requestTime(),
                                              context.requestUser(),
                                              context.requestTime(),
                                              context.requestUser(),
                                              createData.title(),
                                              createData.episodeCount(),
                                              createData.score(),
                                              createData.links());
    }

    public NewAnimeNotificationAction updateWithData(NewAnimeNotificationActionUpdateData updateData,
                                                     RequestContext context) {
        return new NewAnimeNotificationAction(id(),
                                              createdAt(),
                                              createdBy(),
                                              context.requestTime(),
                                              context.requestUser(),
                                              title(),
                                              episodeCount(),
                                              score(),
                                              links());
    }
}
