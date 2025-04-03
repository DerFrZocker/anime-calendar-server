package de.derfrzocker.anime.calendar.server.layer.config;

import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerConfig;
import java.time.Instant;
import java.time.Period;
import org.jetbrains.annotations.NotNull;

public record StreamingTimeLayerConfig(@NotNull Instant startTime, @NotNull Period period, int offset,
                                       @NotNull String type) implements LayerConfig {

}
