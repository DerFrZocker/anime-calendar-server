package de.derfrzocker.anime.calendar.core.integration;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record IntegrationUserId(@NotNull String raw) {

    public IntegrationUserId {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
