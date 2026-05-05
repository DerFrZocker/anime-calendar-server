package de.derfrzocker.anime.calendar.server.layer.common.config;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.parser.LocalizedNameLayerConfigParser;

public record LocalizedNameLayerConfig(LayerKey key, String language, String name) implements LayerConfig {

    public static final LayerParserKey PARSER_KEY = LocalizedNameLayerConfigParser.PARSER_KEY;

    @Override
    public LayerParserKey parserKey() {
        return PARSER_KEY;
    }
}
