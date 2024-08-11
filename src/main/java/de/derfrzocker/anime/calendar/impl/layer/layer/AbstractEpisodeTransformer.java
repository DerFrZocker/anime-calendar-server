package de.derfrzocker.anime.calendar.impl.layer.layer;

import de.derfrzocker.anime.calendar.api.layer.LayerConfigParser;
import de.derfrzocker.anime.calendar.api.layer.Layer;
import de.derfrzocker.anime.calendar.api.layer.LayerConfig;
import de.derfrzocker.anime.calendar.api.layer.LayerDataHolder;
import de.derfrzocker.anime.calendar.api.layer.LayerKey;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractEpisodeTransformer<T extends LayerConfig> implements Layer<T> {

    @NotNull
    private final LayerKey layerKey;
    @NotNull
    private final LayerConfigParser<T> layerConfigParser;

    protected AbstractEpisodeTransformer(@NotNull String layerKey, @NotNull LayerConfigParser<T> layerConfigParser) {
        this.layerKey = new LayerKey(layerKey);
        this.layerConfigParser = layerConfigParser;
    }

    @Override
    public @NotNull LayerKey getLayerKey() {
        return layerKey;
    }

    @Override
    public @NotNull LayerConfigParser<T> getLayerConfigParser() {
        return layerConfigParser;
    }

    @Override
    public @NotNull LayerDataHolder<T> createLayerDataHolder(Map<String, Object> values) {
        return new LayerDataHolder<>(this, getLayerConfigParser().decode(values));
    }
}
