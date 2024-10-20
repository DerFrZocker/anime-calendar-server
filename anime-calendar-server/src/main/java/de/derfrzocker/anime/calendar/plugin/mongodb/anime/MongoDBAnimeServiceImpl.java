package de.derfrzocker.anime.calendar.plugin.mongodb.anime;

import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.server.core.api.layer.LayerService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.event.AnimeAddLayerEvent;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerFilter;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerFilterDataHolder;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerHolder;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerTransformer;
import de.derfrzocker.anime.calendar.utils.StringGenerator;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;
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
    @Inject
    EventBus eventBus;

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
    public Anime createAnime(String animeTitle, int episodeCount, List<Map<String, Object>> episodeLayers) {
        AnimeDO animeDO = new AnimeDO();

        List<LayerHolder> layerHolders = createLayerHolder(episodeLayers);
        List<Map<String, Object>> layerDocuments = parse(layerHolders);

        animeDO.animeTitle = animeTitle;
        animeDO.episodeCount = episodeCount;
        animeDO.episodeLayers = layerDocuments;

        do {
            animeDO.animeId = StringGenerator.generateAnimeId();
        } while (animeDODao.count("_id", animeDO.animeId) > 0);

        animeDODao.persist(animeDO);

        Anime anime = new Anime(animeDO.animeId, animeDO.animeTitle, animeDO.episodeCount, layerHolders);

        eventBus.publish("anime-create", anime);

        return anime;
    }

    @ConsumeEvent("anime-add-layer")
    public void onAnimeAddLayer(AnimeAddLayerEvent event) {
        // TODO 2024-09-24: Reformat code
        Anime old = getAnime(event.animeId());

        AnimeDO animeDO = new AnimeDO();
        animeDO.animeId = event.animeId();
        animeDO.animeTitle = old.animeName();
        animeDO.episodeCount = old.episodeCount();

        List<LayerHolder> layerHolders = new ArrayList<>(old.transformerData());
        layerHolders.add(event.layerHolder());
        animeDO.episodeLayers = parse(layerHolders);

        animeDODao.persistOrUpdate(animeDO);
    }

    private List<Map<String, Object>> parse(List<LayerHolder> layerHolders) {
        List<Map<String, Object>> layerDocuments = new ArrayList<>();
        for (LayerHolder layerHolder : layerHolders) {
            Map<String, Object> map = new LinkedHashMap<>();

            List<Map<String, Object>> filters = layerHolder.filters().stream().map(filterData -> {
                Map<String, Object> filter = filterData.encode();
                filter.put("layer_key", filterData.filter().getLayerKey().raw());
                return filter;
            }).toList();

            if (!filters.isEmpty()) {
                map.put("filters", filters);
            }

            Map<String, Object> transformerData = layerHolder.layerDataHolder().encode();
            transformerData.put("layer_key", layerHolder.layerDataHolder().layer().getLayerKey().raw());
            map.put("transformer", transformerData);

            layerDocuments.add(map);
        }

        return layerDocuments;
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
        if (animeId == null || animeId.raw().isEmpty()) {
            throw new BadRequestException("Anime ID must not be empty");
        }

        for (char c : animeId.raw().toCharArray()) {
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