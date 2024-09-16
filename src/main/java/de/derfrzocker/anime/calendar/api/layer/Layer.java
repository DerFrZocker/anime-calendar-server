package de.derfrzocker.anime.calendar.api.layer;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface Layer<T extends LayerConfig, H> {

    @NotNull
    LayerKey getLayerKey();

    @NotNull
    LayerConfigParser<T> getLayerConfigParser();

    @NotNull
    H createHolder(Map<String, Object> values);
}
