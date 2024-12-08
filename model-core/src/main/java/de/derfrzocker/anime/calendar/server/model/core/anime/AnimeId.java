package de.derfrzocker.anime.calendar.server.model.core.anime;

import de.derfrzocker.anime.calendar.server.model.core.Id;
import de.derfrzocker.anime.calendar.server.model.core.IdType;

public record AnimeId(String raw) implements Id {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'A';

    @Override
    public IdType idType() {
        return IdType.ANIME;
    }
}
