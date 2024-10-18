package de.derfrzocker.anime.calendar.impl.layer.config;

import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerConfig;

public record SimpleOffsetIntegerLayerConfig(int value, int offset) implements LayerConfig {

}
