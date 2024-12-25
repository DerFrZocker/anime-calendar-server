package de.derfrzocker.anime.calendar.server.layer.config;

import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerConfig;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.Period;

public record StreamingTimeLayerConfig(@NotNull Instant startTime, @NotNull Period period, int offset, @NotNull String type) implements LayerConfig {

}
