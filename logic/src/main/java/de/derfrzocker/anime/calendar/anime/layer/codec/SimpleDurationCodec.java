package de.derfrzocker.anime.calendar.anime.layer.codec;

import de.derfrzocker.anime.calendar.anime.LayerConfigCodec;
import de.derfrzocker.anime.calendar.anime.layer.config.SimpleDurationConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import java.time.Duration;

@Identifier(SimpleDurationConfig.DATA_TYPE_RAW)
@ApplicationScoped
class SimpleDurationCodec implements LayerConfigCodec<SimpleDurationConfig> {

    private final static String VALUE_KEY = "value";

    @Override
    public SimpleDurationConfig deserialize(JsonObject data) {

        Duration value = Duration.parse(data.getString(VALUE_KEY));

        return new SimpleDurationConfig(value);
    }

    @Override
    public JsonObject serialize(SimpleDurationConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(VALUE_KEY, data.value().toString());

        return builder.build();
    }
}
