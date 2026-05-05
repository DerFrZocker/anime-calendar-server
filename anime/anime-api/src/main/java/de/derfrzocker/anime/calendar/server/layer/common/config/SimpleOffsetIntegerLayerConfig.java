package de.derfrzocker.anime.calendar.server.layer.common.config;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.parser.SimpleOffsetIntegerLayerConfigParser;

public record SimpleOffsetIntegerLayerConfig(LayerKey key, int value, int offset) implements LayerConfig {

    public static final LayerParserKey PARSER_KEY = SimpleOffsetIntegerLayerConfigParser.PARSER_KEY;

    @Override
    public LayerParserKey parserKey() {
        return PARSER_KEY;
    }
}
