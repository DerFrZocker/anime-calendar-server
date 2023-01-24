package de.derfrzocker.anime.calendar.impl.transformer.config;

public class SimpleIntegerTransformerConfig extends BoundTransformerConfig {

    private final int value;

    public SimpleIntegerTransformerConfig(int minInclusive, int maxInclusive, int value) {
        super(minInclusive, maxInclusive);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
