package de.derfrzocker.anime.calendar.anime.layer.codec;

import de.derfrzocker.anime.calendar.anime.LayerConfigCodec;
import de.derfrzocker.anime.calendar.anime.layer.config.EmptyConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Identifier(EmptyConfig.DATA_TYPE_RAW)
@ApplicationScoped
class EmptyCodec implements LayerConfigCodec<EmptyConfig> {

    @Override
    public EmptyConfig deserialize(JsonObject data) {
        return EmptyConfig.EMPTY;
    }

    @Override
    public JsonObject serialize(EmptyConfig data) {
        return Json.createObjectBuilder().build();
    }
}
