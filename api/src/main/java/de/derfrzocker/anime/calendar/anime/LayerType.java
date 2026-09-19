package de.derfrzocker.anime.calendar.anime;

import java.util.Objects;

public record LayerType(String raw) {

    public static LayerType of(String raw) {
        return new LayerType(raw);
    }

    public LayerType {
        Objects.requireNonNull(raw);
    }
}
