package de.derfrzocker.anime.calendar.impl.transformer.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.api.transformer.ConfigParser;
import de.derfrzocker.anime.calendar.impl.transformer.config.RegionNameTransformerConfig;
import de.derfrzocker.anime.calendar.utils.JsonUtil;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public final class RegionNameConfigParser implements ConfigParser<RegionNameTransformerConfig> {

    public static final RegionNameConfigParser INSTANCE = new RegionNameConfigParser();

    @Override
    public @NotNull RegionNameTransformerConfig fromJson(@NotNull JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) {
            throw new IllegalArgumentException("Expected JsonElement of type JsonObject but got " + jsonElement.getClass());
        }

        int minInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "min-inclusive");
        int maxInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "max-inclusive");

        Map<Region, String> regionNames = new EnumMap<>(Region.class);
        if (jsonElement.getAsJsonObject().has("region-names")) {
            JsonElement regionsElement = jsonElement.getAsJsonObject().get("region-names");
            if (!regionsElement.isJsonObject()) {
                throw new IllegalArgumentException("Expected JsonElement of type JsonObject for key region-names but got " + regionsElement.getClass());
            }

            for (Region region : Region.values()) {
                if (regionsElement.getAsJsonObject().has(region.toString())) {
                    regionNames.put(region, JsonUtil.getStringValue(regionsElement.getAsJsonObject(), region.toString()));
                }
            }
        }

        return new RegionNameTransformerConfig(minInclusive, maxInclusive, regionNames);
    }

    @Override
    public @NotNull JsonElement toJson(@NotNull RegionNameTransformerConfig transformerConfig) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("min-inclusive", transformerConfig.getMinInclusive());
        jsonObject.addProperty("max-inclusive", transformerConfig.getMaxInclusive());

        JsonObject regionNames = new JsonObject();
        for (Map.Entry<Region, String> regionEntry : transformerConfig.getRegionNames().entrySet()) {
            regionNames.addProperty(regionEntry.getKey().toString(), regionEntry.getValue());
        }
        jsonObject.add("region-names", regionNames);

        return jsonObject;
    }
}
