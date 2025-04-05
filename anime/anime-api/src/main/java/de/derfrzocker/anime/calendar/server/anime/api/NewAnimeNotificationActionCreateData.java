package de.derfrzocker.anime.calendar.server.anime.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import java.util.Map;

public record NewAnimeNotificationActionCreateData(String title, int episodeCount, int score,
                                                   Map<IntegrationId, IntegrationAnimeId> links) {

}
