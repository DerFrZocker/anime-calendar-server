package de.derfrzocker.anime.calendar.server.layer.common.config;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.episode.api.StreamType;
import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.parser.StreamingTimeLayerConfigParser;
import java.time.Instant;
import java.time.Period;
import org.jetbrains.annotations.NotNull;

public record StreamingTimeLayerConfig(@NotNull LayerKey key, @NotNull Instant startTime, @NotNull Period period,
                                       int offset, @NotNull StreamType type) implements LayerConfig {

    public static final LayerParserKey PARSER_KEY = StreamingTimeLayerConfigParser.PARSER_KEY;

    @Override
    public LayerParserKey parserKey() {
        return PARSER_KEY;
    }
}
