package de.derfrzocker.anime.calendar.impl.transformer.config;

import de.derfrzocker.anime.calendar.api.transformer.TransformerConfig;

public class BoundTransformerConfig implements TransformerConfig {

    private final int minInclusive;
    private final int maxInclusive;

    public BoundTransformerConfig(int minInclusive, int maxInclusive) {
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
