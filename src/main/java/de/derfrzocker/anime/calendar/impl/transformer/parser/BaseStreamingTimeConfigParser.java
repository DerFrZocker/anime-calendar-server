package de.derfrzocker.anime.calendar.impl.transformer.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.derfrzocker.anime.calendar.api.transformer.ConfigParser;
import de.derfrzocker.anime.calendar.impl.transformer.config.BaseStreamingTimeTransformerConfig;
import de.derfrzocker.anime.calendar.utils.JsonUtil;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.Period;

public final class BaseStreamingTimeConfigParser implements ConfigParser<BaseStreamingTimeTransformerConfig> {

    public static final BaseStreamingTimeConfigParser INSTANCE = new BaseStreamingTimeConfigParser();

    private BaseStreamingTimeConfigParser() {
    }

    @Override
    public @NotNull BaseStreamingTimeTransformerConfig fromJson(@NotNull JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) {
            throw new IllegalArgumentException("Expected JsonElement of type JsonObject but got " + jsonElement.getClass());
        }

        int minInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "min-inclusive");
        int maxInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "max-inclusive");
        String startTime = JsonUtil.getStringValue(jsonElement.getAsJsonObject(), "start-time");
        String period = JsonUtil.getStringValue(jsonElement.getAsJsonObject(), "period");

        return new BaseStreamingTimeTransformerConfig(minInclusive, maxInclusive, Instant.parse(startTime), Period.parse(period));
    }

    @Override
    public @NotNull JsonElement toJson(@NotNull BaseStreamingTimeTransformerConfig transformerConfig) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("min-inclusive", transformerConfig.getMinInclusive());
        jsonObject.addProperty("max-inclusive", transformerConfig.getMaxInclusive());
        jsonObject.addProperty("start-time", transformerConfig.getStartTime().toString());
        jsonObject.addProperty("period", transformerConfig.getPeriod().toString());
        return jsonObject;
    }
}
