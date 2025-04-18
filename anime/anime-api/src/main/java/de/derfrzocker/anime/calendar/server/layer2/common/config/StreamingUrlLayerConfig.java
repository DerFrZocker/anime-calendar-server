package de.derfrzocker.anime.calendar.server.layer2.common.config;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer2.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer2.common.parser.StreamingUrlLayerConfigParser;
import org.jetbrains.annotations.NotNull;

public record StreamingUrlLayerConfig(@NotNull LayerKey key, @NotNull String streamingService,
                                      @NotNull String url) implements LayerConfig {

    public static final LayerParserKey PARSER_KEY = StreamingUrlLayerConfigParser.PARSER_KEY;

    @Override
    public LayerParserKey parserKey() {
        return PARSER_KEY;
    }
}
