package de.derfrzocker.anime.calendar.core.layer;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record LayerParserKey(@NotNull String raw) {

    public LayerParserKey {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
