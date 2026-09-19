package de.derfrzocker.anime.calendar.anime;

import java.util.Objects;

public record LayerConfig<T extends LayerConfigData>(LayerType type, LayerConfigDataType dataType, T data) {

    public LayerConfig {
        Objects.requireNonNull(type);
        Objects.requireNonNull(dataType);
        Objects.requireNonNull(data);
    }
}
