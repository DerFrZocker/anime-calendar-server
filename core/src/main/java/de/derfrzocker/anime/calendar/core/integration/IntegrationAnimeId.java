package de.derfrzocker.anime.calendar.core.integration;

import java.util.Objects;

public record IntegrationAnimeId(String raw) {

    public IntegrationAnimeId {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
