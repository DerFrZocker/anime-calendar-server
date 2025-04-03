package de.derfrzocker.anime.calendar.server.layer.filter;

import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerConfig;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerConfigParser;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerFilter;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerFilterDataHolder;
import de.derfrzocker.anime.calendar.server.layer.AbstractLayer;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractLayerFilter<T extends LayerConfig> extends AbstractLayer<T, LayerFilterDataHolder<T>> implements LayerFilter<T> {

    protected AbstractLayerFilter(@NotNull String layerKey, @NotNull LayerConfigParser<T> layerConfigParser) {
        super(layerKey, layerConfigParser);
    }

    @Override
    public @NotNull LayerFilterDataHolder<T> createHolder(Map<String, Object> values) {
        return new LayerFilterDataHolder<>(this, getLayerConfigParser().decode(values));
    }
}
