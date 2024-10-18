package de.derfrzocker.anime.calendar.impl.layer.transformer;

import de.derfrzocker.anime.calendar.api.layer.LayerConfigParser;
import de.derfrzocker.anime.calendar.api.layer.LayerTransformer;
import de.derfrzocker.anime.calendar.api.layer.LayerConfig;
import de.derfrzocker.anime.calendar.api.layer.LayerTransformerDataHolder;
import de.derfrzocker.anime.calendar.impl.layer.AbstractLayer;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractEpisodeTransformer<T extends LayerConfig> extends AbstractLayer<T, LayerTransformerDataHolder<T>> implements LayerTransformer<T> {

    protected AbstractEpisodeTransformer(@NotNull String layerKey, @NotNull LayerConfigParser<T> layerConfigParser) {
        super(layerKey, layerConfigParser);
    }

    @Override
    public @NotNull LayerTransformerDataHolder<T> createHolder(Map<String, Object> values) {
        return new LayerTransformerDataHolder<>(this, getLayerConfigParser().decode(values));
    }
}
