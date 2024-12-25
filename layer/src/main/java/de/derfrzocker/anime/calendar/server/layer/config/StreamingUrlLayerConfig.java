package de.derfrzocker.anime.calendar.server.layer.config;

import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerConfig;
import org.jetbrains.annotations.NotNull;

public record StreamingUrlLayerConfig(@NotNull String streamingService, @NotNull String url) implements LayerConfig {

}
