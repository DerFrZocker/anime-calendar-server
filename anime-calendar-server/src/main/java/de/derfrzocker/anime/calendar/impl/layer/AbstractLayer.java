package de.derfrzocker.anime.calendar.impl.layer;

import de.derfrzocker.anime.calendar.api.layer.Layer;
import de.derfrzocker.anime.calendar.api.layer.LayerConfig;
import de.derfrzocker.anime.calendar.api.layer.LayerConfigParser;
import de.derfrzocker.anime.calendar.api.layer.LayerKey;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractLayer<T extends LayerConfig, H> implements Layer<T, H> {

    @NotNull
    private final LayerKey layerKey;
    @NotNull
    private final LayerConfigParser<T> layerConfigParser;

    protected AbstractLayer(@NotNull String layerKey, @NotNull LayerConfigParser<T> layerConfigParser) {
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
}
