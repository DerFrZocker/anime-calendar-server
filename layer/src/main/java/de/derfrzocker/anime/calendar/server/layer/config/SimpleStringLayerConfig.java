package de.derfrzocker.anime.calendar.server.layer.config;

import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerConfig;
import org.jetbrains.annotations.NotNull;

public record SimpleStringLayerConfig(@NotNull String value) implements LayerConfig {

}
