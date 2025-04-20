package de.derfrzocker.anime.calendar.server.layer.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.layer.api.LayerFilter;
import de.derfrzocker.anime.calendar.server.layer.common.filter.BoundLayerFilter;
import de.derfrzocker.anime.calendar.server.layer.common.filter.RegionLayerFilter;
import de.derfrzocker.anime.calendar.server.layer.service.LayerFilterService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class LayerFilterServiceImpl implements LayerFilterService {

    private final Map<LayerKey, LayerFilter<?>> layerFilters = new HashMap<>();

    @Override
    public Optional<LayerFilter<?>> getByKey(LayerKey key, RequestContext context) {
        return Optional.ofNullable(this.layerFilters.get(key));
    }

    private void register(LayerKey key, LayerFilter<?> filter) {
        this.layerFilters.put(key, filter);
    }

    @PostConstruct
    void registerLayers() {
        register(BoundLayerFilter.LAYER_KEY, BoundLayerFilter.INSTANCE);
        register(RegionLayerFilter.LAYER_KEY, RegionLayerFilter.INSTANCE);
    }
}
