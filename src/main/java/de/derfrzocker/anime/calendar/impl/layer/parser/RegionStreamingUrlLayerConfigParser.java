package de.derfrzocker.anime.calendar.impl.layer.parser;

import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.impl.layer.config.RegionStreamingUrlLayerConfig;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class RegionStreamingUrlLayerConfigParser extends AbstractLayerConfigParser<RegionStreamingUrlLayerConfig> {

    public static final RegionStreamingUrlLayerConfigParser INSTANCE = new RegionStreamingUrlLayerConfigParser();

    private RegionStreamingUrlLayerConfigParser() {
    }

    @Override
    public @NotNull RegionStreamingUrlLayerConfig decode(@NotNull Map<String, Object> values) {

        int minInclusive = decodeInt(values, "min-inclusive");
        int maxInclusive = decodeInt(values, "max-inclusive");
        String streamingService = decodeString(values, "streaming-service");
        String url = decodeString(values, "url");
        Set<Region> applicableRegions = decodeRegions(values, "applicable-regions");

        return new RegionStreamingUrlLayerConfig(minInclusive, maxInclusive, streamingService, applicableRegions, url);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull RegionStreamingUrlLayerConfig transformerConfig) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("min-inclusive", transformerConfig.getMinInclusive());
        result.put("max-inclusive", transformerConfig.getMaxInclusive());
        result.put("streaming-service", transformerConfig.getStreamingService());
        result.put("url", transformerConfig.getUrl());
        result.put("applicable-regions", encode(transformerConfig.getApplicableRegions()));

        return result;
    }
}
