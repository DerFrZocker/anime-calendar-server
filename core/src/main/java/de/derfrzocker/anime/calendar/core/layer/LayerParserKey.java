package de.derfrzocker.anime.calendar.core.layer;

import java.util.Objects;

public record LayerParserKey(String raw) {

    public LayerParserKey {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
