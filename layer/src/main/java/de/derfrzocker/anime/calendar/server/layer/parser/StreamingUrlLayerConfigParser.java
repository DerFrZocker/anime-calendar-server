package de.derfrzocker.anime.calendar.server.layer.parser;

import de.derfrzocker.anime.calendar.server.layer.config.StreamingUrlLayerConfig;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class StreamingUrlLayerConfigParser extends AbstractLayerConfigParser<StreamingUrlLayerConfig> {

    public static final StreamingUrlLayerConfigParser INSTANCE = new StreamingUrlLayerConfigParser();

    private StreamingUrlLayerConfigParser() {
    }

    @Override
    public @NotNull StreamingUrlLayerConfig decode(@NotNull Map<String, Object> values) {

        String streamingService = decodeString(values, "streaming-service");
        String url = decodeString(values, "url");

        return new StreamingUrlLayerConfig(streamingService, url);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull StreamingUrlLayerConfig transformerConfig) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("streaming-service", transformerConfig.streamingService());
        result.put("url", transformerConfig.url());

        return result;
    }
}
