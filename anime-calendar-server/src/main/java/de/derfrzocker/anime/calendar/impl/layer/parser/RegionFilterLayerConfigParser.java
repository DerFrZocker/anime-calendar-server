package de.derfrzocker.anime.calendar.impl.layer.parser;

import de.derfrzocker.anime.calendar.impl.layer.config.RegionFilterConfig;
import de.derfrzocker.anime.calendar.server.model.domain.ical.Region;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public final class RegionFilterLayerConfigParser extends AbstractLayerConfigParser<RegionFilterConfig> {

    private final static String APPLICABLE_REGIONS = "applicable-regions";
    public static final RegionFilterLayerConfigParser INSTANCE = new RegionFilterLayerConfigParser();

    private RegionFilterLayerConfigParser() {
    }

    @Override
    public @NotNull RegionFilterConfig decode(@NotNull Map<String, Object> values) {
        return new RegionFilterConfig(decodeRegions(values, APPLICABLE_REGIONS));
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull RegionFilterConfig layerConfig) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put(APPLICABLE_REGIONS, encode(layerConfig.applicableRegions()));

        return result;
    }

    private Set<Region> decodeRegions(Map<String, Object> values, String key) {
        Set<Region> result = EnumSet.noneOf(Region.class);
        if (values.containsKey(key)) {
            Collection<String> regions = decodeCollection(values, key);
            regions.stream().map(Region::valueOf).forEach(result::add);
        }

        return result;
    }

    private Object encode(Collection<Region> values) {
        return values.stream().map(Enum::toString).toList();
    }
}
