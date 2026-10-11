package de.derfrzocker.anime.calendar.anime.layer.codec;

import de.derfrzocker.anime.calendar.anime.LayerConfigCodec;
import de.derfrzocker.anime.calendar.anime.layer.config.SimpleIntegerConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Identifier(SimpleIntegerConfig.DATA_TYPE_RAW)
@ApplicationScoped
class SimpleIntegerCodec implements LayerConfigCodec<SimpleIntegerConfig> {

    private final static String VALUE_KEY = "value";

    @Override
    public SimpleIntegerConfig deserialize(JsonObject data) {

        int value = data.getInt(VALUE_KEY);

        return new SimpleIntegerConfig(value);
    }

    @Override
    public JsonObject serialize(SimpleIntegerConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(VALUE_KEY, data.value());

        return builder.build();
    }
}
