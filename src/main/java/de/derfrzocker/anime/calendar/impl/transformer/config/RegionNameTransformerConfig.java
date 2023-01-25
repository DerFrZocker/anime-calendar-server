package de.derfrzocker.anime.calendar.impl.transformer.config;

import de.derfrzocker.anime.calendar.api.Region;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class RegionNameTransformerConfig extends BoundTransformerConfig {

    @NotNull
    private final Set<@NotNull Region> applicableRegions;
    @NotNull
    private final String name;


    public RegionNameTransformerConfig(int minInclusive, int maxInclusive, @NotNull Set<@NotNull Region> applicableRegions, @NotNull String name) {
        super(minInclusive, maxInclusive);
        this.applicableRegions = applicableRegions;
        this.name = name;
    }

    public @NotNull Set<@NotNull Region> getApplicableRegions() {
        return applicableRegions;
    }

    @NotNull
    public String getName() {
        return name;
    }
}
