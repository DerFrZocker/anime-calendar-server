package de.derfrzocker.anime.calendar.anime.layer.config;

import de.derfrzocker.anime.calendar.anime.LayerConfigData;
import de.derfrzocker.anime.calendar.anime.LayerConfigDataType;
import de.derfrzocker.anime.calendar.anime.StreamType;
import java.time.Instant;
import java.time.Period;
import java.util.Objects;

public record StreamingTimeConfig(Instant startTime, Period period, int offset, StreamType type)
        implements LayerConfigData {

    public static final String DATA_TYPE_RAW = "streaming_time";
    public static final LayerConfigDataType DATA_TYPE = LayerConfigDataType.of(DATA_TYPE_RAW);

    public StreamingTimeConfig {
        Objects.requireNonNull(startTime);
        Objects.requireNonNull(period);
        Objects.requireNonNull(type);
    }
}
