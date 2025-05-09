package de.derfrzocker.anime.calendar.server.layer.parser;

import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface LayerConfigParser<T extends LayerConfig> {

    @NotNull
    T decode(@NotNull Map<String, Object> values);

    @NotNull
    Map<String, Object> encode(@NotNull T config);
}
