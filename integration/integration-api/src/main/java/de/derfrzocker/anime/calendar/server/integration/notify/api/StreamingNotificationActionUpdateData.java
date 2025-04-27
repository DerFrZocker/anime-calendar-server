package de.derfrzocker.anime.calendar.server.integration.notify.api;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.util.Change;
import java.time.Instant;

public record StreamingNotificationActionUpdateData(Change<IntegrationId> integrationId,
                                                    Change<IntegrationAnimeId> integrationAnimeId,
                                                    Change<Integer> streamingEpisode, Change<Instant> streamingTime) {

}
