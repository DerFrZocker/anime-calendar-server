package de.derfrzocker.anime.calendar.server.layer2.common.parser;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer2.common.config.StreamingUrlLayerConfig;
import de.derfrzocker.anime.calendar.server.layer2.parser.AbstractLayerConfigParser;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class StreamingUrlLayerConfigParser extends AbstractLayerConfigParser<StreamingUrlLayerConfig> {

    public static final LayerParserKey PARSER_KEY = new LayerParserKey("streaming-url-layer");
    public static final StreamingUrlLayerConfigParser INSTANCE = new StreamingUrlLayerConfigParser();

    private static final String STREAMING_SERVICE_KEY = "streaming-service";
    private static final String URL_KEY = "url";

    private StreamingUrlLayerConfigParser() {
    }

    @Override
    public @NotNull StreamingUrlLayerConfig decode(@NotNull Map<String, Object> values) {

        LayerKey key = decodeLayerKey(values);
        String streamingService = decodeString(values, STREAMING_SERVICE_KEY);
        String url = decodeString(values, URL_KEY);

        return new StreamingUrlLayerConfig(key, streamingService, url);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull StreamingUrlLayerConfig config) {
        Map<String, Object> values = new LinkedHashMap<>();

        encodeLayerKey(values, config.key());
        encodeString(values, STREAMING_SERVICE_KEY, config.streamingService());
        encodeString(values, URL_KEY, config.url());

        return values;
    }
}
