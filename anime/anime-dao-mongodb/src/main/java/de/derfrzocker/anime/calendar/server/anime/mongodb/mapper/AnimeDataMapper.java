package de.derfrzocker.anime.calendar.server.anime.mongodb.mapper;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.mongodb.data.AnimeDO;
import de.derfrzocker.anime.calendar.server.anime.mongodb.parser.LayerParser;
import de.derfrzocker.anime.calendar.server.layer2.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer2.api.LayerStepConfig;
import de.derfrzocker.anime.calendar.server.layer2.service.LayerConfigParserService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class AnimeDataMapper {

    private static final String FILTERS_KEY = "filters";
    private static final String TRANSFORMER_KEY = "transformer";

    private AnimeDataMapper() {
    }

    public static AnimeDO toData(Anime domain, LayerConfigParserService configParserService, LayerParser layerParser) {
        AnimeDO data = new AnimeDO();

        data.id = domain.id();
        data.title = domain.title();
        data.episodeCount = domain.episodeCount();
        data.episodeLayers = layerParser.parse(domain.oldEpisodeLayers());
        data.newEpisodeLayers = toData(domain.episodeLayers(), configParserService);
        data.apply(domain);

        return data;
    }

    private static List<Map<String, Object>> toData(List<LayerStepConfig> episodeLayers,
                                                    LayerConfigParserService configParserService) {
        List<Map<String, Object>> data = new ArrayList<>();

        for (LayerStepConfig episodeLayer : episodeLayers) {
            data.add(toData(episodeLayer, configParserService));
        }

        return data;
    }

    private static Map<String, Object> toData(LayerStepConfig episodeLayer,
                                              LayerConfigParserService configParserService) {
        Map<String, Object> data = new LinkedHashMap<>();
        List<Map<String, Object>> filters = new ArrayList<>();

        for (LayerConfig filterConfig : episodeLayer.filterConfigs()) {
            filters.add(configParserService.encode(filterConfig));
        }

        if (!filters.isEmpty()) {
            data.put(FILTERS_KEY, filters);
        }

        data.put(TRANSFORMER_KEY, configParserService.encode(episodeLayer.transformConfig()));

        return data;
    }

    public static Anime toDomain(AnimeDO data, LayerConfigParserService configParserService, LayerParser layerParser) {
        String title = data.title;
        if (title == null) {
            title = data.animeTitle;
        }

        return new Anime(data.id,
                         data.createdAt,
                         data.createdBy,
                         data.updatedAt,
                         data.updatedBy,
                         title,
                         data.episodeCount,
                         toDomain(data.newEpisodeLayers, configParserService),
                         layerParser.createLayerHolder(data.episodeLayers));
    }

    private static List<LayerStepConfig> toDomain(List<Map<String, Object>> data,
                                                  LayerConfigParserService configParserService) {
        if (data == null || data.isEmpty()) {
            return new ArrayList<>();
        }

        List<LayerStepConfig> domain = new ArrayList<>();

        for (Map<String, Object> item : data) {
            domain.add(toDomain(item, configParserService));
        }

        return domain;
    }

    private static LayerStepConfig toDomain(Map<String, Object> data, LayerConfigParserService configParserService) {
        List<LayerConfig> filters = new ArrayList<>();
        Object filtersObject = data.get(FILTERS_KEY);

        if (filtersObject != null) {
            // TODO 2025-04-19: Check how this can be better cast with checks
            List<Map<String, Object>> filtersData = (List<Map<String, Object>>) filtersObject;
            for (Map<String, Object> filter : filtersData) {
                filters.add(configParserService.decode(filter));
            }
        }

        Object transformerObject = data.get(TRANSFORMER_KEY);
        // TODO 2025-04-19: Check how this can be better cast with checks
        Map<String, Object> transformerData = (Map<String, Object>) transformerObject;
        LayerConfig transformer = configParserService.decode(transformerData);

        return new LayerStepConfig(filters, transformer);
    }
}
