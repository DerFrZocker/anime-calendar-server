package de.derfrzocker.anime.calendar.anime;

import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public record Episode(
        int index,
        String name,
        @Nullable Duration length,
        @Nullable String episode,
        @Nullable String episodes,
        @Nullable URI animeListLink,
        @Nullable URI streamingLink,
        @Nullable Instant streamingTime) {

    public Episode {
        Objects.requireNonNull(name);
        Objects.requireNonNull(length);
    }
}
