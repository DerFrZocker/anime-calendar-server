package de.derfrzocker.anime.calendar.layer.common.config;

import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.layer.api.LayerConfig;
import de.derfrzocker.anime.calendar.layer.common.parser.IntegrationUrlLayerConfigParser;

public record IntegrationUrlLayerConfig(LayerKey key, IntegrationId integrationId, String url) implements LayerConfig {

    public static final LayerParserKey PARSER_KEY = IntegrationUrlLayerConfigParser.PARSER_KEY;

    @Override
    public LayerParserKey parserKey() {
        return PARSER_KEY;
    }
}
