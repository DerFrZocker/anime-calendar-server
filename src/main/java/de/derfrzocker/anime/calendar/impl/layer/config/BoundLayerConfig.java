package de.derfrzocker.anime.calendar.impl.layer.config;

import de.derfrzocker.anime.calendar.api.layer.LayerConfig;

public class BoundLayerConfig implements LayerConfig {

    private final int minInclusive;
    private final int maxInclusive;

    public BoundLayerConfig(int minInclusive, int maxInclusive) {
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

    public int getMinInclusive() {
        return minInclusive;
    }

    public int getMaxInclusive() {
        return maxInclusive;
    }
}
