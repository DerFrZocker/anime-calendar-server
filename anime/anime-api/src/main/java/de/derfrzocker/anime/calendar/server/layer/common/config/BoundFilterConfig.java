package de.derfrzocker.anime.calendar.server.layer.common.config;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.parser.BoundFilterConfigParser;
import org.jetbrains.annotations.NotNull;

public record BoundFilterConfig(@NotNull LayerKey key, int minInclusive, int maxInclusive) implements LayerConfig {

    public static final LayerParserKey PARSER_KEY = BoundFilterConfigParser.PARSER_KEY;
    public static final int ALL_EPISODES = -1;

    @Override
    public LayerParserKey parserKey() {
        return PARSER_KEY;
    }
}
