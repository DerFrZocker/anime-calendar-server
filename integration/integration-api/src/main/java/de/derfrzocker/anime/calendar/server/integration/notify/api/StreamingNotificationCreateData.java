package de.derfrzocker.anime.calendar.server.integration.notify.api;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import java.time.Instant;

public record StreamingNotificationCreateData(AnimeId animeId, int orgEpisodeIndex, Instant orgStreamingTime,
                                              IntegrationId referenceIntegrationId,
                                              IntegrationAnimeId referenceIntegrationAnimeId) {

}
