package de.derfrzocker.anime.calendar.impl.layer.config;

import de.derfrzocker.anime.calendar.api.layer.LayerConfig;

public record SimpleOffsetIntegerLayerConfig(int value, int offset) implements LayerConfig {

}
