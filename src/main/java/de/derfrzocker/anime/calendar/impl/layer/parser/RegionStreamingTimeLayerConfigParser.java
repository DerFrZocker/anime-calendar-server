package de.derfrzocker.anime.calendar.impl.layer.parser;

import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.impl.layer.config.RegionStreamingTimeLayerConfig;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.Period;
import java.util.Set;

public final class RegionStreamingTimeLayerConfigParser extends AbstractLayerConfigParser<RegionStreamingTimeLayerConfig> {

    public static final RegionStreamingTimeLayerConfigParser INSTANCE = new RegionStreamingTimeLayerConfigParser();

    private RegionStreamingTimeLayerConfigParser() {
    }

    @Override
    public @NotNull RegionStreamingTimeLayerConfig decode(@NotNull Map<String, Object> values) {

        int minInclusive = decodeInt(values, "min-inclusive");
        int maxInclusive = decodeInt(values, "max-inclusive");
        String streamingService = decodeString(values, "streaming-service");
        String type = decodeString(values, "type");
        String startTime = decodeString(values, "start-time");
        String period = decodeString(values, "period");
        Set<Region> applicableRegions = decodeRegions(values, "applicable-regions");

        return new RegionStreamingTimeLayerConfig(minInclusive, maxInclusive, streamingService, applicableRegions, type, Instant.parse(startTime), Period.parse(period));
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull RegionStreamingTimeLayerConfig transformerConfig) {
        Map<String, Object> result = new HashMap<>();

        result.put("min-inclusive", transformerConfig.getMinInclusive());
        result.put("max-inclusive", transformerConfig.getMaxInclusive());
        result.put("streaming-service", transformerConfig.getStreamingService());
        result.put("type", transformerConfig.getType());
        result.put("start-time", transformerConfig.getStartTime().toString());
        result.put("period", transformerConfig.getPeriod().toString());
        result.put("applicable-regions", encode(transformerConfig.getApplicableRegions()));

        return result;
    }
}
