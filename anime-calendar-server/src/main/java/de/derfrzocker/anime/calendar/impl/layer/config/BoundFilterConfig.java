package de.derfrzocker.anime.calendar.impl.layer.config;

import de.derfrzocker.anime.calendar.api.layer.LayerConfig;

public record BoundFilterConfig(int minInclusive, int maxInclusive) implements LayerConfig {

}
