package de.derfrzocker.anime.calendar.server.model.domain.calendar;

import de.derfrzocker.anime.calendar.server.model.core.AnimeId;

public record AnimeOverride(AnimeId animeId, boolean include) {
}
