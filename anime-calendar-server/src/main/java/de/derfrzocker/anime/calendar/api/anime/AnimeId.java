package de.derfrzocker.anime.calendar.api.anime;

import de.derfrzocker.anime.calendar.api.Id;
import de.derfrzocker.anime.calendar.api.IdType;

public record AnimeId(String id) implements Id {

    @Override
    public IdType idType() {
        return IdType.ANIME;
    }
}