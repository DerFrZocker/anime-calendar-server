package de.derfrzocker.anime.calendar.impl.transformer.config;

import de.derfrzocker.anime.calendar.api.Region;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RegionNameTransformerConfig extends BoundTransformerConfig {

    @NotNull
    private final Map<@NotNull Region, @NotNull String> regionNames;

    public RegionNameTransformerConfig(int minInclusive, int maxInclusive, @NotNull Map<@NotNull Region, @NotNull String> regionNames) {
        super(minInclusive, maxInclusive);
        this.regionNames = regionNames;
    }

    @NotNull
    public Map<@NotNull Region, @NotNull String> getRegionNames() {
        return regionNames;
    }
}
