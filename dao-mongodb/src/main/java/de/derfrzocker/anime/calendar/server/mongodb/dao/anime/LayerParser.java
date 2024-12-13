package de.derfrzocker.anime.calendar.server.mongodb.dao.anime;

import de.derfrzocker.anime.calendar.server.core.api.layer.LayerService;
import de.derfrzocker.anime.calendar.server.model.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerFilter;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerFilterDataHolder;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerHolder;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerTransformer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// TODO 2024-12-09: Make this whole better
@RequestScoped
public class LayerParser {

    private static final String LAYER_KEY = "layer_key";
    private static final String FILTERS = "filters";
    private static final String TRANSFORMER = "transformer";

    @Inject
    LayerService layerService;

    public List<LayerHolder> createLayerHolder(List<Map<String, Object>> episodeLayers) {
        List<LayerHolder> layerHolders = new ArrayList<>();
        for (Map<String, Object> map : episodeLayers) {
            List<LayerFilterDataHolder<?>> filterDataHolders = readFilters(map);
            Map<String, Object> transformerData = (Map<String, Object>) map.get(TRANSFORMER);
            LayerKey transformerKey = new LayerKey((String) transformerData.get(LAYER_KEY));
            LayerTransformer<?> transformer = layerService.getLayerTransformer(transformerKey);

            if (transformer == null) {
                throw new IllegalArgumentException("Layer " + transformerKey + " not found");
            }

            layerHolders.add(new LayerHolder(filterDataHolders, transformer.createHolder(transformerData)));
        }

        return layerHolders;
    }

    public List<Map<String, Object>> parse(List<LayerHolder> layerHolders) {
        List<Map<String, Object>> layerDocuments = new ArrayList<>();
        for (LayerHolder layerHolder : layerHolders) {
            Map<String, Object> map = new LinkedHashMap<>();

            List<Map<String, Object>> filters = layerHolder.filters().stream().map(filterData -> {
                Map<String, Object> filter = filterData.encode();
                filter.put(LAYER_KEY, filterData.filter().getLayerKey().raw());
                return filter;
            }).toList();

            if (!filters.isEmpty()) {
                map.put(FILTERS, filters);
            }

            Map<String, Object> transformerData = layerHolder.layerDataHolder().encode();
            transformerData.put(LAYER_KEY, layerHolder.layerDataHolder().layer().getLayerKey().raw());
            map.put(TRANSFORMER, transformerData);

            layerDocuments.add(map);
        }

        return layerDocuments;
    }

    private List<LayerFilterDataHolder<?>> readFilters(Map<String, Object> map) {
        List<Map<String, Object>> filters = (List<Map<String, Object>>) map.get(FILTERS);

        if (filters == null) {
            return Collections.emptyList();
        }

        List<LayerFilterDataHolder<?>> result = new ArrayList<>();
        filters.stream().map(data -> {
            LayerKey filterKey = new LayerKey((String) data.get(LAYER_KEY));
            LayerFilter<?> filter = layerService.getLayerFilter(filterKey);

            if (filter == null) {
                throw new IllegalArgumentException("Layer " + filterKey + " not found");
            }

            return (LayerFilterDataHolder<?>) filter.createHolder(data);
        }).forEach(result::add);

        return result;
    }
}
