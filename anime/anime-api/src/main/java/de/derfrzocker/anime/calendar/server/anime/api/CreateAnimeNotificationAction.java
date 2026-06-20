package de.derfrzocker.anime.calendar.server.anime.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;

public record CreateAnimeNotificationAction(
        NotificationActionId id,
        String title,
        int score,
        IntegrationId integrationId,
        IntegrationAnimeId integrationAnimeId) {

}
