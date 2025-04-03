package de.derfrzocker.anime.calendar.server.layer.transformer;

import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerConfig;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerConfigParser;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerTransformer;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerTransformerDataHolder;
import de.derfrzocker.anime.calendar.server.layer.AbstractLayer;
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
