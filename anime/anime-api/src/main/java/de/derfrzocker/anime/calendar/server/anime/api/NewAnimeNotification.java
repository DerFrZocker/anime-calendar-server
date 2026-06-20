package de.derfrzocker.anime.calendar.server.anime.api;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationType;

public record NewAnimeNotification(
        NotificationId id, IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {

    public static final String NOTIFICATION_TYPE_RAW = "NewAnime";
    public static final NotificationType NOTIFICATION_TYPE = new NotificationType(NOTIFICATION_TYPE_RAW);

    public static NewAnimeNotification from(
            NotificationId id,
            NewAnimeNotificationCreateData createData,
            RequestContext context) {

        return new NewAnimeNotification(id, createData.integrationId(), createData.integrationAnimeId());
    }
}
