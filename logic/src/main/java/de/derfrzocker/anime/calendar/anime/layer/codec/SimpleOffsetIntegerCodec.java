package de.derfrzocker.anime.calendar.anime.layer.codec;

import de.derfrzocker.anime.calendar.anime.LayerConfigCodec;
import de.derfrzocker.anime.calendar.anime.layer.config.SimpleOffsetIntegerConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Identifier(SimpleOffsetIntegerConfig.DATA_TYPE_RAW)
@ApplicationScoped
class SimpleOffsetIntegerCodec implements LayerConfigCodec<SimpleOffsetIntegerConfig> {

    private final static String VALUE_KEY = "value";
    private final static String OFFSET_KEY = "offset";

    @Override
    public SimpleOffsetIntegerConfig deserialize(JsonObject data) {

        int value = data.getInt(VALUE_KEY);
        int offset = data.getInt(OFFSET_KEY);

        return new SimpleOffsetIntegerConfig(value, offset);
    }

    @Override
    public JsonObject serialize(SimpleOffsetIntegerConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(VALUE_KEY, data.value());
        builder.add(OFFSET_KEY, data.offset());

        return builder.build();
    }
}
