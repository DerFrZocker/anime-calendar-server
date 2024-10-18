package de.derfrzocker.anime.calendar.impl.layer.filter;

import de.derfrzocker.anime.calendar.impl.layer.AbstractLayer;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerConfig;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerConfigParser;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerFilter;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerFilterDataHolder;
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
