package de.derfrzocker.anime.calendar.server.layer.parser;

import de.derfrzocker.anime.calendar.server.layer.config.IntegrationUrlLayerConfig;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class IntegrationUrlLayerConfigParser extends AbstractLayerConfigParser<IntegrationUrlLayerConfig> {

    public static final IntegrationUrlLayerConfigParser INSTANCE = new IntegrationUrlLayerConfigParser();

    private IntegrationUrlLayerConfigParser() {
    }

    @Override
    public @NotNull IntegrationUrlLayerConfig decode(@NotNull Map<String, Object> values) {

        String integrationId = decodeString(values, "integration-id");
        String url = decodeString(values, "url");

        return new IntegrationUrlLayerConfig(integrationId, url);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull IntegrationUrlLayerConfig transformerConfig) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("integration-id", transformerConfig.integrationId());
        result.put("url", transformerConfig.url());

        return result;
    }
}
