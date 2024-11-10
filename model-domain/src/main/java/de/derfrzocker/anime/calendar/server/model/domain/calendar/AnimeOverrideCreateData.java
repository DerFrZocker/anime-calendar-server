package de.derfrzocker.anime.calendar.server.model.domain.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;

public record AnimeOverrideCreateData(AnimeId animeId, boolean include) {

}
