package de.derfrzocker.anime.calendar.impl.layer.config;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.Period;

public class BaseStreamingTimeLayerConfig extends BoundLayerConfig {

    @NotNull
    private final Instant startTime;

    @NotNull
    private final Period period;

    public BaseStreamingTimeLayerConfig(int minInclusive, int maxInclusive, @NotNull Instant startTime, @NotNull Period period) {
        super(minInclusive, maxInclusive);
        this.startTime = startTime;
        this.period = period;
    }

    @NotNull
    public Instant getStartTime() {
        return startTime;
    }

    @NotNull
    public Period getPeriod() {
        return period;
    }
}
