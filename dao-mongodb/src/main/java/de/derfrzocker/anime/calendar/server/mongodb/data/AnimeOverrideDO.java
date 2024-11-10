package de.derfrzocker.anime.calendar.server.mongodb.data;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.AnimeOverrideID;

public record AnimeOverrideDO(AnimeOverrideID id, AnimeId animeId, boolean include) {

}
