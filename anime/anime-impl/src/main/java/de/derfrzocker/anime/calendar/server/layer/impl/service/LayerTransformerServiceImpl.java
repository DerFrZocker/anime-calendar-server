package de.derfrzocker.anime.calendar.server.layer.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.layer.api.LayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.EpisodeLengthLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.EpisodeNumberLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.IntegrationUrlLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.LocalizedNameLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.StreamingTimeLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.StreamingUrlLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.service.LayerTransformerService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class LayerTransformerServiceImpl implements LayerTransformerService {

    private final Map<LayerKey, LayerTransformer<?>> layerTransformers = new HashMap<>();

    @Override
    public Optional<LayerTransformer<?>> getByKey(LayerKey key, RequestContext context) {
        return Optional.ofNullable(this.layerTransformers.get(key));
    }

    private void register(LayerKey key, LayerTransformer<?> transformer) {
        this.layerTransformers.put(key, transformer);
    }

    @PostConstruct
    void registerLayers() {
        register(EpisodeLengthLayerTransformer.LAYER_KEY, EpisodeLengthLayerTransformer.INSTANCE);
        register(EpisodeNumberLayerTransformer.LAYER_KEY, EpisodeNumberLayerTransformer.INSTANCE);
        register(IntegrationUrlLayerTransformer.LAYER_KEY, IntegrationUrlLayerTransformer.INSTANCE);
        register(LocalizedNameLayerTransformer.LAYER_KEY, LocalizedNameLayerTransformer.INSTANCE);
        register(StreamingTimeLayerTransformer.LAYER_KEY, StreamingTimeLayerTransformer.INSTANCE);
        register(StreamingUrlLayerTransformer.LAYER_KEY, StreamingUrlLayerTransformer.INSTANCE);
    }
}
