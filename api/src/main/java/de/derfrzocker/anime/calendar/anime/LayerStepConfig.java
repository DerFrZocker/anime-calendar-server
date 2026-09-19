package de.derfrzocker.anime.calendar.anime;

import java.util.List;
import java.util.Objects;

public record LayerStepConfig(List<LayerConfig<?>> filters, LayerConfig<?> transformer) {

    public LayerStepConfig {
        Objects.requireNonNull(filters);
        Objects.requireNonNull(transformer);
    }
}
