package de.derfrzocker.anime.calendar.impl.transformer.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.derfrzocker.anime.calendar.api.transformer.ConfigParser;
import de.derfrzocker.anime.calendar.impl.transformer.config.SimpleIntegerTransformerConfig;
import de.derfrzocker.anime.calendar.utils.JsonUtil;
import org.jetbrains.annotations.NotNull;

public final class SimpleIntegerConfigParser implements ConfigParser<SimpleIntegerTransformerConfig> {

    public static final SimpleIntegerConfigParser INSTANCE = new SimpleIntegerConfigParser();

    private SimpleIntegerConfigParser() {
    }

    @Override
    public @NotNull SimpleIntegerTransformerConfig fromJson(@NotNull JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) {
            throw new IllegalArgumentException("Expected JsonElement of type JsonObject but got " + jsonElement.getClass());
        }

        int minInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "min-inclusive");
        int maxInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "max-inclusive");
        int value = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "value");

        return new SimpleIntegerTransformerConfig(minInclusive, maxInclusive, value);
    }

    @Override
    public @NotNull JsonElement toJson(@NotNull SimpleIntegerTransformerConfig transformerConfig) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("min-inclusive", transformerConfig.getMinInclusive());
        jsonObject.addProperty("max-inclusive", transformerConfig.getMaxInclusive());
        jsonObject.addProperty("value", transformerConfig.getValue());
        return jsonObject;
    }
}
