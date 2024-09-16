package de.derfrzocker.anime.calendar.api.calendar;

import de.derfrzocker.anime.calendar.api.anime.AnimeId;

public record AnimeOverride(AnimeId animeId, boolean include) {
}
