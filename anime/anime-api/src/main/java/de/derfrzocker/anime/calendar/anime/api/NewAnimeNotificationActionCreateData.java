package de.derfrzocker.anime.calendar.anime.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;

public record NewAnimeNotificationActionCreateData(
        String title,
        int episodeCount,
        int score,
        IntegrationId sourceIntegrationId,
        IntegrationAnimeId sourceIntegrationAnimeId,
        IntegrationId integrationId,
        IntegrationAnimeId integrationAnimeId) {

}
