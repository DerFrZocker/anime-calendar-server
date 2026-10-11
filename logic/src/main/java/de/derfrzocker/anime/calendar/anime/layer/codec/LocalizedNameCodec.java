package de.derfrzocker.anime.calendar.anime.layer.codec;

import de.derfrzocker.anime.calendar.anime.LayerConfigCodec;
import de.derfrzocker.anime.calendar.anime.layer.config.LocalizedNameConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Identifier(LocalizedNameConfig.DATA_TYPE_RAW)
@ApplicationScoped
class LocalizedNameCodec implements LayerConfigCodec<LocalizedNameConfig> {

    private final static String LANGUAGE_KEY = "language";
    private final static String NAME_KEY = "name";

    @Override
    public LocalizedNameConfig deserialize(JsonObject data) {

        String language = data.getString(LANGUAGE_KEY);
        String name = data.getString(NAME_KEY);

        return new LocalizedNameConfig(language, name);
    }

    @Override
    public JsonObject serialize(LocalizedNameConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(LANGUAGE_KEY, data.language());
        builder.add(NAME_KEY, data.name());

        return builder.build();
    }
}
