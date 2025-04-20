package de.derfrzocker.anime.calendar.server.layer.common.config;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.anime.api.Region;
import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.parser.RegionFilterConfigParser;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public record RegionFilterConfig(LayerKey key, Set<@NotNull Region> applicableRegions) implements LayerConfig {

    private static final LayerParserKey PARSER_KEY = RegionFilterConfigParser.PARSER_KEY;

    @Override
    public LayerParserKey parserKey() {
        return PARSER_KEY;
    }
}
