package de.derfrzocker.anime.calendar.impl.transformer.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.api.transformer.ConfigParser;
import de.derfrzocker.anime.calendar.impl.transformer.config.RegionStreamingTimeTransformerConfig;
import de.derfrzocker.anime.calendar.utils.JsonUtil;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.Period;
import java.util.EnumSet;
import java.util.Set;

public final class RegionStreamingTimeConfigParser implements ConfigParser<RegionStreamingTimeTransformerConfig> {

    public static final RegionStreamingTimeConfigParser INSTANCE = new RegionStreamingTimeConfigParser();

    private RegionStreamingTimeConfigParser() {
    }

    @Override
    public @NotNull RegionStreamingTimeTransformerConfig fromJson(@NotNull JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) {
            throw new IllegalArgumentException("Expected JsonElement of type JsonObject but got " + jsonElement.getClass());
        }

        int minInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "min-inclusive");
        int maxInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "max-inclusive");
        String streamingService = JsonUtil.getStringValue(jsonElement.getAsJsonObject(), "streaming-service");
        String type = JsonUtil.getStringValue(jsonElement.getAsJsonObject(), "type");
        String startTime = JsonUtil.getStringValue(jsonElement.getAsJsonObject(), "start-time");
        String period = JsonUtil.getStringValue(jsonElement.getAsJsonObject(), "period");

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

        return new RegionStreamingTimeTransformerConfig(minInclusive, maxInclusive, streamingService, applicableRegions, type, Instant.parse(startTime), Period.parse(period));
    }

    @Override
    public @NotNull JsonElement toJson(@NotNull RegionStreamingTimeTransformerConfig transformerConfig) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("min-inclusive", transformerConfig.getMinInclusive());
        jsonObject.addProperty("max-inclusive", transformerConfig.getMaxInclusive());
        jsonObject.addProperty("streaming-service", transformerConfig.getStreamingService());
        jsonObject.addProperty("type", transformerConfig.getType());
        jsonObject.addProperty("start-time", transformerConfig.getStartTime().toString());
        jsonObject.addProperty("period", transformerConfig.getPeriod().toString());

        JsonArray applicableRegions = new JsonArray(transformerConfig.getApplicableRegions().size());
        for (Region region : transformerConfig.getApplicableRegions()) {
            applicableRegions.add(region.toString());
        }

        jsonObject.add("applicable-regions", applicableRegions);

        return jsonObject;
    }
}
