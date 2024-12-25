package de.derfrzocker.anime.calendar.server.layer.transformer;

import de.derfrzocker.anime.calendar.server.layer.AbstractLayer;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerConfig;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerConfigParser;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerTransformer;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerTransformerDataHolder;
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
