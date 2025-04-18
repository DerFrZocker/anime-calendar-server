package de.derfrzocker.anime.calendar.server.layer2.common.config;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer2.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer2.common.parser.SimpleStringLayerConfigParser;
import org.jetbrains.annotations.NotNull;

public record SimpleStringLayerConfig(@NotNull LayerKey key, @NotNull String value) implements LayerConfig {

    public static final LayerParserKey PARSER_KEY = SimpleStringLayerConfigParser.PARSER_KEY;

    @Override
    public LayerParserKey parserKey() {
        return PARSER_KEY;
    }
}
