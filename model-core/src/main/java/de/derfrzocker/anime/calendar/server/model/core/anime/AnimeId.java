package de.derfrzocker.anime.calendar.server.model.core.anime;

import de.derfrzocker.anime.calendar.server.model.core.Id;
import de.derfrzocker.anime.calendar.server.model.core.IdType;

public record AnimeId(String raw) implements Id {

    @Override
    public IdType idType() {
        return IdType.ANIME;
    }
}
