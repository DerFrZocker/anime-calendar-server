package de.derfrzocker.anime.calendar.impl.transformer.config;

import de.derfrzocker.anime.calendar.api.Region;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.Period;
import java.util.Set;

public class RegionStreamingTimeTransformerConfig extends BoundTransformerConfig {


    @NotNull
    private final String streamingService;
    @NotNull
    private final Set<@NotNull Region> applicableRegions;
    @NotNull
    private final String type;
    @NotNull
    private final Instant startTime;
    @NotNull
    private final Period period;

    public RegionStreamingTimeTransformerConfig(int minInclusive, int maxInclusive, @NotNull String streamingService, @NotNull Set<@NotNull Region> applicableRegions, @NotNull String type, @NotNull Instant startTime, @NotNull Period period) {
        super(minInclusive, maxInclusive);
        this.streamingService = streamingService;
        this.applicableRegions = applicableRegions;
        this.type = type;
        this.startTime = startTime;
        this.period = period;
    }

    @NotNull
    public String getStreamingService() {
        return streamingService;
    }

    @NotNull
    public Set<@NotNull Region> getApplicableRegions() {
        return applicableRegions;
    }

    @NotNull
    public Instant getStartTime() {
        return startTime;
    }

    @NotNull
    public Period getPeriod() {
        return period;
    }

    @NotNull
    public String getType() {
        return type;
    }
}
