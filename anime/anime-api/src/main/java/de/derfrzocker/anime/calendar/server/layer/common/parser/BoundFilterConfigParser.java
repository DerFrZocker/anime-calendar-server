package de.derfrzocker.anime.calendar.server.layer.common.parser;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer.common.config.BoundFilterConfig;
import de.derfrzocker.anime.calendar.server.layer.parser.AbstractLayerConfigParser;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class BoundFilterConfigParser extends AbstractLayerConfigParser<BoundFilterConfig> {

    public static final LayerParserKey PARSER_KEY = new LayerParserKey("bound-filter");
    public static final BoundFilterConfigParser INSTANCE = new BoundFilterConfigParser();

    private static final String MIN_INCLUSIVE_KEY = "min-inclusive";
    private static final String MAX_INCLUSIVE_KEY = "max-inclusive";

    private BoundFilterConfigParser() {
    }

    @Override
    public @NotNull BoundFilterConfig decode(@NotNull Map<String, Object> values) {

        LayerKey key = decodeLayerKey(values);
        int minInclusive = decodeInt(values, MIN_INCLUSIVE_KEY);
        int maxInclusive = decodeInt(values, MAX_INCLUSIVE_KEY);

        return new BoundFilterConfig(key, minInclusive, maxInclusive);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull BoundFilterConfig config) {
        Map<String, Object> values = new LinkedHashMap<>();

        encodeLayerKey(values, config.key());
        encodeInt(values, MIN_INCLUSIVE_KEY, config.minInclusive());
        encodeInt(values, MAX_INCLUSIVE_KEY, config.maxInclusive());

        return values;
    }
}
