package de.derfrzocker.anime.calendar.server.impl.season.anidb.client;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import java.time.Instant;

public record AniDBSeasonInfo(IntegrationAnimeId integrationAnimeId, Instant startDate) {

}