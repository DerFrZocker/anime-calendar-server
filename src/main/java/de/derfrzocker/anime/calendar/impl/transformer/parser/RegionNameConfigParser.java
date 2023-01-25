package de.derfrzocker.anime.calendar.impl.transformer.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.api.transformer.ConfigParser;
import de.derfrzocker.anime.calendar.impl.transformer.config.RegionNameTransformerConfig;
import de.derfrzocker.anime.calendar.utils.JsonUtil;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public final class RegionNameConfigParser implements ConfigParser<RegionNameTransformerConfig> {

    public static final RegionNameConfigParser INSTANCE = new RegionNameConfigParser();

    @Override
    public @NotNull RegionNameTransformerConfig fromJson(@NotNull JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) {
            throw new IllegalArgumentException("Expected JsonElement of type JsonObject but got " + jsonElement.getClass());
        }

        int minInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "min-inclusive");
        int maxInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "max-inclusive");
        String name = JsonUtil.getStringValue(jsonElement.getAsJsonObject(), "name");

        Set<Region> applicableRegions = EnumSet.noneOf(Region.class);
        if (jsonElement.getAsJsonObject().has("applicable-regions")) {
            JsonElement applicableRegionsElement = jsonElement.getAsJsonObject().get("applicable-regions");
            if (!applicableRegionsElement.isJsonArray()) {
                throw new IllegalArgumentException("Expected JsonElement of type JsonArray for key applicable-regions but got " + applicableRegionsElement.getClass());
            }

            for (Region region : Region.values()) {
                if (applicableRegionsElement.getAsJsonArray().contains(new JsonPrimitive(region.toString()))) {
                    applicableRegions.add(region);
                }
            }
        }

        return new RegionNameTransformerConfig(minInclusive, maxInclusive, applicableRegions, name);
    }

    @Override
    public @NotNull JsonElement toJson(@NotNull RegionNameTransformerConfig transformerConfig) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("min-inclusive", transformerConfig.getMinInclusive());
        jsonObject.addProperty("max-inclusive", transformerConfig.getMaxInclusive());
        jsonObject.addProperty("name", transformerConfig.getName());

        JsonArray applicableRegions = new JsonArray(transformerConfig.getApplicableRegions().size());
        for (Region region : transformerConfig.getApplicableRegions()) {
            applicableRegions.add(region.toString());
        }

        jsonObject.add("applicable-regions", applicableRegions);

        return jsonObject;
    }
}
