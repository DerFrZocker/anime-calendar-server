package de.derfrzocker.anime.calendar.plugin.mongodb.anime;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import de.derfrzocker.anime.calendar.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.api.layer.LayerFilter;
import de.derfrzocker.anime.calendar.api.layer.LayerFilterDataHolder;
import de.derfrzocker.anime.calendar.api.layer.LayerHolder;
import de.derfrzocker.anime.calendar.api.layer.LayerTransformer;
import de.derfrzocker.anime.calendar.api.layer.LayerKey;
import de.derfrzocker.anime.calendar.api.layer.LayerService;
import de.derfrzocker.anime.calendar.utils.StringGenerator;
import de.derfrzocker.anime.calendar.web.request.anime.AnimePostRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.Nullable;

@ApplicationScoped
public class MongoDBAnimeServiceImpl implements AnimeService {

    @Inject
    AnimeDODao animeDODao;
    @Inject
    LayerService layerService;

    @Override
    public @Nullable Anime getAnime(AnimeId id) {
        validateId(id);
        AnimeDO animeDO = animeDODao.findById(id);

        if (animeDO == null) {
            return null;
        }

        return new Anime(animeDO.animeId, animeDO.animeTitle, animeDO.episodeCount, createLayerHolder(animeDO.episodeLayers));
    }

    private List<LayerHolder> createLayerHolder(List<Map<String, Object>> episodeLayers) {
        List<LayerHolder> layerHolders = new ArrayList<>();
        for (Map<String, Object> map : episodeLayers) {
            List<LayerFilterDataHolder<?>> filterDataHolders = readFilters(map);
            Map<String, Object> transformerData = (Map<String, Object>) map.get("transformer");
            LayerKey transformerKey = new LayerKey((String) transformerData.get("layer_key"));
            LayerTransformer<?> transformer = layerService.getLayerTransformer(transformerKey);

            if (transformer == null) {
                throw new IllegalArgumentException("Layer " + transformerKey + " not found");
            }

            layerHolders.add(new LayerHolder(filterDataHolders, transformer.createHolder(transformerData)));
        }

        return layerHolders;
    }

    private List<LayerFilterDataHolder<?>> readFilters(Map<String, Object> map) {
        List<Map<String, Object>> filters = (List<Map<String, Object>>) map.get("filters");

        if (filters == null) {
            return Collections.emptyList();
        }

        List<LayerFilterDataHolder<?>> result = new ArrayList<>();
        filters.stream().map(data -> {
            LayerKey filterKey = new LayerKey((String) data.get("layer_key"));
            LayerFilter<?> filter = layerService.getLayerFilter(filterKey);

            if (filter == null) {
                throw new IllegalArgumentException("Layer " + filterKey + " not found");
            }

            return (LayerFilterDataHolder<?>) filter.createHolder(data);
        }).forEach(result::add);

        return result;
    }

    @Override
    public boolean isAnime(AnimeId id) {
        validateId(id);
        return animeDODao.count("_id", id) >= 1;
    }

    @Override
    public Anime createAnime(AnimePostRequest animePostRequest) {
        AnimeDO animeDO = new AnimeDO();

        List<LayerHolder> layerHolders = createLayerHolder(animePostRequest.episodeLayers());
        List<Map<String, Object>> layerDocuments = new ArrayList<>();
        for (LayerHolder layerHolder : layerHolders) {
            Map<String, Object> map = new LinkedHashMap<>();

            List<Map<String, Object>> filters = layerHolder.filters().stream().map(filterData -> {
                Map<String, Object> filter = filterData.encode();
                filter.put("layer_key", filterData.filter().getLayerKey());
                return filter;
            }).toList();

            if (!filters.isEmpty()) {
                map.put("filters", filters);
            }

            Map<String, Object> transformerData = layerHolder.layerDataHolder().encode();
            transformerData.put("layer_key", layerHolder.layerDataHolder().layer().getLayerKey().key());
            map.put("transformer", transformerData);

            layerDocuments.add(map);
        }

        animeDO.animeTitle = animePostRequest.animeTitle();
        animeDO.episodeCount = animePostRequest.episodeCount();
        animeDO.episodeLayers = layerDocuments;

        do {
            animeDO.animeId = StringGenerator.generateAnimeId();
        } while (animeDODao.count("_id", animeDO.animeId) > 0);

        animeDODao.persist(animeDO);

        return new Anime(animeDO.animeId, animeDO.animeTitle, animeDO.episodeCount, layerHolders);
    }

    private String generateId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        char[] result = new char[10];

        for (int i = 0; i < result.length; i++) {
            result[i] = chars.charAt((ThreadLocalRandom.current().nextInt(chars.length())));
        }

        return new String(result);
    }

    private void validateId(AnimeId animeId) {
        if (animeId == null || animeId.id().isEmpty()) {
            throw new BadRequestException("Anime ID must not be empty");
        }

        for (char c : animeId.id().toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                continue;
            }

            if (c >= '0' && c <= '9') {
                continue;
            }

            throw new BadRequestException("Invalid anime ID: " + animeId);
        }
    }
}