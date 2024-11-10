package de.derfrzocker.anime.calendar.server.rest.transfer.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.AnimeOverrideID;

public record AnimeOverrideTO(AnimeOverrideID id, AnimeId animeId, boolean include) {

}
