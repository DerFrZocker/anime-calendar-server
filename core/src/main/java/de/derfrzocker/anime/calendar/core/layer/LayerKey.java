package de.derfrzocker.anime.calendar.core.layer;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record LayerKey(@NotNull String raw) {

    public LayerKey {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
