package de.derfrzocker.anime.calendar.external;

import java.util.Objects;

public record ExternalAnimeId(String raw) {

    public static ExternalAnimeId of(String raw) {
        return new ExternalAnimeId(raw);
    }

    public ExternalAnimeId {
        Objects.requireNonNull(raw);
    }
}
