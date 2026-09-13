package de.derfrzocker.anime.calendar.external;

import java.util.Objects;
import java.util.UUID;

public record ExternalAnimeNameId(UUID raw) {

    public ExternalAnimeNameId {
        Objects.requireNonNull(raw);
    }

    public static ExternalAnimeNameId of(UUID raw) {
        return new ExternalAnimeNameId(raw);
    }
}
