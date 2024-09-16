package de.derfrzocker.anime.calendar.impl.layer.config;

import de.derfrzocker.anime.calendar.api.layer.LayerConfig;
import org.jetbrains.annotations.NotNull;

public record SimpleStringLayerConfig(@NotNull String value) implements LayerConfig {

}
