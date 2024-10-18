package de.derfrzocker.anime.calendar.server.model.domain.layer;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface LayerConfigParser<T extends LayerConfig> {

    @NotNull
    T decode(@NotNull Map<String, Object> values);

    @NotNull
    Map<String, Object> encode(@NotNull T layerConfig);
}
