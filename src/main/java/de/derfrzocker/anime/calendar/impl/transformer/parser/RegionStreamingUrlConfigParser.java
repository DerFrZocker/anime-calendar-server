package de.derfrzocker.anime.calendar.impl.transformer.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.api.transformer.ConfigParser;
import de.derfrzocker.anime.calendar.impl.transformer.config.RegionStreamingUrlTransformerConfig;
import de.derfrzocker.anime.calendar.utils.JsonUtil;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Set;

public final class RegionStreamingUrlConfigParser implements ConfigParser<RegionStreamingUrlTransformerConfig> {

    public static final RegionStreamingUrlConfigParser INSTANCE = new RegionStreamingUrlConfigParser();

    private RegionStreamingUrlConfigParser() {
    }

    @Override
    public @NotNull RegionStreamingUrlTransformerConfig fromJson(@NotNull JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) {
            throw new IllegalArgumentException("Expected JsonElement of type JsonObject but got " + jsonElement.getClass());
        }

        int minInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "min-inclusive");
        int maxInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "max-inclusive");
        String streamingService = JsonUtil.getStringValue(jsonElement.getAsJsonObject(), "streaming-service");
        String url = JsonUtil.getStringValue(jsonElement.getAsJsonObject(), "url");

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

        return new RegionStreamingUrlTransformerConfig(minInclusive, maxInclusive, streamingService, applicableRegions, url);
    }

    @Override
    public @NotNull JsonElement toJson(@NotNull RegionStreamingUrlTransformerConfig transformerConfig) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("min-inclusive", transformerConfig.getMinInclusive());
        jsonObject.addProperty("max-inclusive", transformerConfig.getMaxInclusive());
        jsonObject.addProperty("streaming-service", transformerConfig.getStreamingService());
        jsonObject.addProperty("url", transformerConfig.getUrl());

        JsonArray applicableRegions = new JsonArray(transformerConfig.getApplicableRegions().size());
        for (Region region : transformerConfig.getApplicableRegions()) {
            applicableRegions.add(region.toString());
        }

        jsonObject.add("applicable-regions", applicableRegions);

        return jsonObject;
    }
}
