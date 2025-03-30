package de.derfrzocker.anime.calendar.server.model.domain.layer;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
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
