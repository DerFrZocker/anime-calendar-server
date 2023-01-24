package de.derfrzocker.anime.calendar.impl.transformer.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.derfrzocker.anime.calendar.api.transformer.ConfigParser;
import de.derfrzocker.anime.calendar.impl.transformer.config.BoundTransformerConfig;
import de.derfrzocker.anime.calendar.utils.JsonUtil;
import org.jetbrains.annotations.NotNull;

public final class BoundConfigParser implements ConfigParser<BoundTransformerConfig> {

    public static final BoundConfigParser INSTANCE = new BoundConfigParser();

    private BoundConfigParser() {
    }

    @Override
    public @NotNull BoundTransformerConfig fromJson(@NotNull JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) {
            throw new IllegalArgumentException("Expected JsonElement of type JsonObject but got " + jsonElement.getClass());
        }

        int minInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "min-inclusive");
        int maxInclusive = JsonUtil.getIntValue(jsonElement.getAsJsonObject(), "max-inclusive");

        return new BoundTransformerConfig(minInclusive, maxInclusive);
    }

    @Override
    public @NotNull JsonElement toJson(@NotNull BoundTransformerConfig transformerConfig) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("min-inclusive", transformerConfig.getMinInclusive());
        jsonObject.addProperty("max-inclusive", transformerConfig.getMaxInclusive());
        return jsonObject;
    }
}
