package de.derfrzocker.anime.calendar.anime.layer.codec;

import de.derfrzocker.anime.calendar.anime.LayerConfigCodec;
import de.derfrzocker.anime.calendar.anime.layer.config.SimpleStringConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Identifier(SimpleStringConfig.DATA_TYPE_RAW)
@ApplicationScoped
class SimpleStringCodec implements LayerConfigCodec<SimpleStringConfig> {

    private final static String VALUE_KEY = "value";

    @Override
    public SimpleStringConfig deserialize(JsonObject data) {

        String value = data.getString(VALUE_KEY);

        return new SimpleStringConfig(value);
    }

    @Override
    public JsonObject serialize(SimpleStringConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(VALUE_KEY, data.value());

        return builder.build();
    }
}
