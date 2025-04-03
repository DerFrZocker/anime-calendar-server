package de.derfrzocker.anime.calendar.server.layer.config;

import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerConfig;

public record BoundFilterConfig(int minInclusive, int maxInclusive) implements LayerConfig {

}
