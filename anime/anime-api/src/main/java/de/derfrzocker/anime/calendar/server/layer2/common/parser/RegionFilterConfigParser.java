package de.derfrzocker.anime.calendar.server.layer2.common.parser;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.anime.api.Region;
import de.derfrzocker.anime.calendar.server.layer2.common.config.RegionFilterConfig;
import de.derfrzocker.anime.calendar.server.layer2.parser.AbstractLayerConfigParser;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public final class RegionFilterConfigParser extends AbstractLayerConfigParser<RegionFilterConfig> {

    public static final LayerParserKey PARSER_KEY = new LayerParserKey("region-filter");
    public static final RegionFilterConfigParser INSTANCE = new RegionFilterConfigParser();

    private static final String APPLICABLE_REGIONS_KEY = "applicable-regions";

    private RegionFilterConfigParser() {
    }

    @Override
    public @NotNull RegionFilterConfig decode(@NotNull Map<String, Object> values) {

        LayerKey key = decodeLayerKey(values);
        Set<Region> applicableRegions = decodeRegions(values, APPLICABLE_REGIONS_KEY);

        return new RegionFilterConfig(key, applicableRegions);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull RegionFilterConfig config) {
        Map<String, Object> values = new LinkedHashMap<>();

        encodeLayerKey(values, config.key());
        encodeRegions(values, APPLICABLE_REGIONS_KEY, config.applicableRegions());

        return values;
    }

    private Set<Region> decodeRegions(Map<String, Object> values, String key) {
        try (Stream<String> stream = decodeStringStream(values, key)) {
            return stream.map(Region::valueOf).collect(Collectors.toSet());
        }
    }

    private void encodeRegions(Map<String, Object> values, String key, Set<Region> value) {
        encodeStringStream(values, key, value.stream().map(Enum::name));
    }
}
