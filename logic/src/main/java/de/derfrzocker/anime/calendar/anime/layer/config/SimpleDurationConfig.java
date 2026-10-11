package de.derfrzocker.anime.calendar.anime.layer.config;

import de.derfrzocker.anime.calendar.anime.LayerConfigData;
import de.derfrzocker.anime.calendar.anime.LayerConfigDataType;
import java.time.Duration;
import java.util.Objects;

public record SimpleDurationConfig(Duration value) implements LayerConfigData {

    public static final String DATA_TYPE_RAW = "simple_duration";
    public static final LayerConfigDataType DATA_TYPE = LayerConfigDataType.of(DATA_TYPE_RAW);

    public SimpleDurationConfig {
        Objects.requireNonNull(value);
    }
}
