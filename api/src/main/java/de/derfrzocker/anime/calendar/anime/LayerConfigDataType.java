package de.derfrzocker.anime.calendar.anime;

import java.util.Objects;

public record LayerConfigDataType(String raw) {

    public static LayerConfigDataType of(String raw) {
        return new LayerConfigDataType(raw);
    }

    public LayerConfigDataType {
        Objects.requireNonNull(raw);
    }
}
