package de.derfrzocker.anime.calendar.impl.layer.parser;

import de.derfrzocker.anime.calendar.impl.layer.config.BaseStreamingTimeLayerConfig;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.Period;

public final class BaseStreamingTimeLayerConfigParser extends AbstractLayerConfigParser<BaseStreamingTimeLayerConfig> {

    public static final BaseStreamingTimeLayerConfigParser INSTANCE = new BaseStreamingTimeLayerConfigParser();

    private BaseStreamingTimeLayerConfigParser() {
    }

    @Override
    public @NotNull BaseStreamingTimeLayerConfig decode(@NotNull Map<String, Object> values) {

        int minInclusive = decodeInt(values, "min-inclusive");
        int maxInclusive = decodeInt(values, "max-inclusive");
        String startTime = decodeString(values, "start-time");
        String period = decodeString(values,"period");

        return new BaseStreamingTimeLayerConfig(minInclusive, maxInclusive, Instant.parse(startTime), Period.parse(period));
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull BaseStreamingTimeLayerConfig layerConfig) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("min-inclusive", layerConfig.getMinInclusive());
        result.put("max-inclusive", layerConfig.getMaxInclusive());
        result.put("start-time", layerConfig.getStartTime().toString());
        result.put("period", layerConfig.getPeriod().toString());

        return result;
    }
}
