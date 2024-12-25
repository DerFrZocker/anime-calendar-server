package de.derfrzocker.anime.calendar.server.layer.parser;

import de.derfrzocker.anime.calendar.server.layer.config.StreamingTimeLayerConfig;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.Period;

public final class StreamingTimeLayerConfigParser extends AbstractLayerConfigParser<StreamingTimeLayerConfig> {

    public static final StreamingTimeLayerConfigParser INSTANCE = new StreamingTimeLayerConfigParser();

    private StreamingTimeLayerConfigParser() {
    }

    @Override
    public @NotNull StreamingTimeLayerConfig decode(@NotNull Map<String, Object> values) {

        String startTime = decodeString(values, "start-time");
        String period = decodeString(values,"period", "P7D");
        int startIndex = decodeInt(values,"offset", 0);
        String type = decodeString(values,"type", "org");

        return new StreamingTimeLayerConfig(Instant.parse(startTime), Period.parse(period), startIndex, type);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull StreamingTimeLayerConfig layerConfig) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("start-time", layerConfig.startTime().toString());
        result.put("period", layerConfig.period().toString());
        result.put("offset", layerConfig.offset());
        result.put("type", layerConfig.type());

        return result;
    }
}
