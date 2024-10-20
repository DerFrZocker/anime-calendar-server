package de.derfrzocker.anime.calendar.collect.anidb.season.udp;

import de.derfrzocker.anime.calendar.collect.anidb.ExternalAnimeId;
import java.time.Instant;

public record SeasonData(ExternalAnimeId externalAnimeId, Instant startDate) {
}
