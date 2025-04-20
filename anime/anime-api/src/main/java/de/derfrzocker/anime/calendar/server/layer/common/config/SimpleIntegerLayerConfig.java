package de.derfrzocker.anime.calendar.server.layer.common.config;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.parser.SimpleIntegerLayerConfigParser;
import org.jetbrains.annotations.NotNull;

public record SimpleIntegerLayerConfig(@NotNull LayerKey key, int value) implements LayerConfig {

    public static final LayerParserKey PARSER_KEY = SimpleIntegerLayerConfigParser.PARSER_KEY;

    @Override
    public LayerParserKey parserKey() {
        return PARSER_KEY;
    }
}
