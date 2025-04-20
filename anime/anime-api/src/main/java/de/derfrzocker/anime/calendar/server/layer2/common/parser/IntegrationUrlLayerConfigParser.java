package de.derfrzocker.anime.calendar.server.layer2.common.parser;

import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer2.common.config.IntegrationUrlLayerConfig;
import de.derfrzocker.anime.calendar.server.layer2.parser.AbstractLayerConfigParser;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class IntegrationUrlLayerConfigParser extends AbstractLayerConfigParser<IntegrationUrlLayerConfig> {

    public static final LayerParserKey PARSER_KEY = new LayerParserKey("integration-url-layer");
    public static final IntegrationUrlLayerConfigParser INSTANCE = new IntegrationUrlLayerConfigParser();

    private static final String INTEGRATION_ID_KEY = "integration-id";
    private static final String URL_KEY = "url";

    private IntegrationUrlLayerConfigParser() {
    }

    @Override
    public @NotNull IntegrationUrlLayerConfig decode(@NotNull Map<String, Object> values) {

        LayerKey key = decodeLayerKey(values);
        String integrationId = decodeString(values, INTEGRATION_ID_KEY);
        String url = decodeString(values, URL_KEY);

        return new IntegrationUrlLayerConfig(key, new IntegrationId(integrationId), url);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull IntegrationUrlLayerConfig config) {
        Map<String, Object> values = new LinkedHashMap<>();

        encodeLayerKey(values, config.key());
        encodeString(values, INTEGRATION_ID_KEY, config.integrationId().raw());
        encodeString(values, URL_KEY, config.url());

        return values;
    }
}
