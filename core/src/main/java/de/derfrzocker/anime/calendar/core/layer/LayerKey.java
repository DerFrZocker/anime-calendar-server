package de.derfrzocker.anime.calendar.core.layer;

import java.util.Objects;

public record LayerKey(String raw) {

    public LayerKey {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
