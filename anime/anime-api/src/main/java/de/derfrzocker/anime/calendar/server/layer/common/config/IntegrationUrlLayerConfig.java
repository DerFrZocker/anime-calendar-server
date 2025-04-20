package de.derfrzocker.anime.calendar.server.layer.common.config;

import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.parser.IntegrationUrlLayerConfigParser;
import org.jetbrains.annotations.NotNull;

public record IntegrationUrlLayerConfig(LayerKey key, @NotNull IntegrationId integrationId,
                                        @NotNull String url) implements LayerConfig {

    public static final LayerParserKey PARSER_KEY = IntegrationUrlLayerConfigParser.PARSER_KEY;

    @Override
    public LayerParserKey parserKey() {
        return PARSER_KEY;
    }
}
