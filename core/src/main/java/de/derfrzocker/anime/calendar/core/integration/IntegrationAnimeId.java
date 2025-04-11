package de.derfrzocker.anime.calendar.core.integration;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record IntegrationAnimeId(@NotNull String raw) {

    public IntegrationAnimeId {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
