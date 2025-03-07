package de.derfrzocker.anime.calendar.server.integration.api;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;

public record IntegrationLinkNotificationActionCreateData(AnimeId animeId, IntegrationId integrationId,
                                                          IntegrationAnimeId integrationAnimeId, int score,
                                                          String bestName) {

}
