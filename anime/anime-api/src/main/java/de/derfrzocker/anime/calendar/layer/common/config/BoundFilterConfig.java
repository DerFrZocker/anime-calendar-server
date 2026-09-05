package de.derfrzocker.anime.calendar.layer.common.config;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.layer.api.LayerConfig;
import de.derfrzocker.anime.calendar.layer.common.parser.BoundFilterConfigParser;

public record BoundFilterConfig(LayerKey key, int minInclusive, int maxInclusive) implements LayerConfig {

    public static final LayerParserKey PARSER_KEY = BoundFilterConfigParser.PARSER_KEY;
    public static final int ALL_EPISODES = -1;

    @Override
    public LayerParserKey parserKey() {
        return PARSER_KEY;
    }
}
