package de.derfrzocker.anime.calendar.anime;

import java.util.Objects;
import java.util.UUID;

public record AnimeId(UUID raw) {

    public static AnimeId of(UUID raw) {
        return new AnimeId(raw);
    }

    public AnimeId {
        Objects.requireNonNull(raw);
    }
}
