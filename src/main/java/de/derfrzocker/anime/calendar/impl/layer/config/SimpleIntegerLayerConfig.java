package de.derfrzocker.anime.calendar.impl.layer.config;

public class SimpleIntegerLayerConfig extends BoundLayerConfig {

    private final int value;

    public SimpleIntegerLayerConfig(int minInclusive, int maxInclusive, int value) {
        super(minInclusive, maxInclusive);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
