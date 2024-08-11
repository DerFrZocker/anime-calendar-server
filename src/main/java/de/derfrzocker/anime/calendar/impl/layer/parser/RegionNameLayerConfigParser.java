package de.derfrzocker.anime.calendar.impl.layer.parser;

import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.impl.layer.config.RegionNameLayerConfig;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class RegionNameLayerConfigParser extends AbstractLayerConfigParser<RegionNameLayerConfig> {

    public static final RegionNameLayerConfigParser INSTANCE = new RegionNameLayerConfigParser();

    @Override
    public @NotNull RegionNameLayerConfig decode(@NotNull Map<String, Object> values) {

        int minInclusive = decodeInt(values, "min-inclusive");
        int maxInclusive = decodeInt(values, "max-inclusive");
        String name = decodeString(values, "name");
        Set<Region> applicableRegions = decodeRegions(values, "applicable-regions");

        return new RegionNameLayerConfig(minInclusive, maxInclusive, applicableRegions, name);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull RegionNameLayerConfig transformerConfig) {
        Map<String, Object> result = new HashMap<>();

        result.put("min-inclusive", transformerConfig.getMinInclusive());
        result.put("max-inclusive", transformerConfig.getMaxInclusive());
        result.put("name", transformerConfig.getName());
        result.put("applicable-regions", encode(transformerConfig.getApplicableRegions()));

        return result;
    }
}
