package de.derfrzocker.anime.calendar.impl.season.anidb.client;

import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import java.time.Instant;

public record AniDBSeasonInfo(IntegrationAnimeId integrationAnimeId, Instant startDate) {

}
