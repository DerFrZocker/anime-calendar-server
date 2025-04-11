package de.derfrzocker.anime.calendar.core.integration;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record IntegrationId(@NotNull String raw) {

    public IntegrationId {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
