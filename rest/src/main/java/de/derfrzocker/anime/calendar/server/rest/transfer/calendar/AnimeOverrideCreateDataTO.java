package de.derfrzocker.anime.calendar.server.rest.transfer.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;

public record AnimeOverrideCreateDataTO(AnimeId animeId, boolean include) {

}
