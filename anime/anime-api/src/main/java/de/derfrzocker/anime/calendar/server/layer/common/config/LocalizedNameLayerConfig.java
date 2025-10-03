package de.derfrzocker.anime.calendar.server.layer.common.config;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.parser.LocalizedNameLayerConfigParser;
import org.jetbrains.annotations.NotNull;

public record LocalizedNameLayerConfig(@NotNull LayerKey key, @NotNull String language,
                                       @NotNull String name) implements LayerConfig {

    public static final LayerParserKey PARSER_KEY = LocalizedNameLayerConfigParser.PARSER_KEY;

    @Override
    public LayerParserKey parserKey() {
        return PARSER_KEY;
    }
}
