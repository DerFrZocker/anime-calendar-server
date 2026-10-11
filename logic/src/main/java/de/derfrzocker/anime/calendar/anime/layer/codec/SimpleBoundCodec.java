package de.derfrzocker.anime.calendar.anime.layer.codec;

import de.derfrzocker.anime.calendar.anime.LayerConfigCodec;
import de.derfrzocker.anime.calendar.anime.layer.config.SimpleBoundConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Identifier(SimpleBoundConfig.DATA_TYPE_RAW)
@ApplicationScoped
class SimpleBoundCodec implements LayerConfigCodec<SimpleBoundConfig> {

    private final static String MIN_INCLUSIVE_KEY = "min_inclusive";
    private final static String MAX_INCLUSIVE_KEY = "max_inclusive";

    @Override
    public SimpleBoundConfig deserialize(JsonObject data) {

        int minInclusive = data.getInt(MIN_INCLUSIVE_KEY);
        int maxInclusive = data.getInt(MAX_INCLUSIVE_KEY);

        return new SimpleBoundConfig(minInclusive, maxInclusive);
    }

    @Override
    public JsonObject serialize(SimpleBoundConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(MIN_INCLUSIVE_KEY, data.minInclusive());
        builder.add(MAX_INCLUSIVE_KEY, data.maxInclusive());

        return builder.build();
    }
}
