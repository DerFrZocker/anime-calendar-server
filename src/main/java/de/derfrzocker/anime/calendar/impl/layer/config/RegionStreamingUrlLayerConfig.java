package de.derfrzocker.anime.calendar.impl.layer.config;

import de.derfrzocker.anime.calendar.api.Region;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class RegionStreamingUrlLayerConfig extends BoundLayerConfig {

    @NotNull
    private final String streamingService;
    @NotNull
    private final Set<@NotNull Region> applicableRegions;
    @NotNull
    private final String url;

    public RegionStreamingUrlLayerConfig(int minInclusive, int maxInclusive, @NotNull String streamingService, @NotNull Set<@NotNull Region> applicableRegions, @NotNull String url) {
        super(minInclusive, maxInclusive);
        this.streamingService = streamingService;
        this.applicableRegions = applicableRegions;
        this.url = url;
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
    public String getUrl() {
        return url;
    }
}
