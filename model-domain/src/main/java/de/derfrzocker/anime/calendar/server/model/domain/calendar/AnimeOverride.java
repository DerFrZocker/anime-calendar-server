package de.derfrzocker.anime.calendar.server.model.domain.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.AnimeOverrideID;

public record AnimeOverride(AnimeOverrideID id, AnimeId animeId, boolean include) {

    public static AnimeOverride from(AnimeOverrideID id, AnimeOverrideCreateData data) {
        return new AnimeOverride(id, data.animeId(), data.include());
    }
}
