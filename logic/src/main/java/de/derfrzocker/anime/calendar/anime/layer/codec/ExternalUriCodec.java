package de.derfrzocker.anime.calendar.anime.layer.codec;

import de.derfrzocker.anime.calendar.anime.LayerConfigCodec;
import de.derfrzocker.anime.calendar.anime.layer.config.ExternalUriConfig;
import de.derfrzocker.anime.calendar.external.ExternalType;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import java.net.URI;

@Identifier(ExternalUriConfig.DATA_TYPE_RAW)
@ApplicationScoped
class ExternalUriCodec implements LayerConfigCodec<ExternalUriConfig> {

    private final static String EXTERNAL_TYPE_KEY = "external_type";
    private final static String URI_KEY = "uri";

    @Override
    public ExternalUriConfig deserialize(JsonObject data) {

        ExternalType externalType = ExternalType.of(data.getString(EXTERNAL_TYPE_KEY));
        URI uri = URI.create(data.getString(URI_KEY));

        return new ExternalUriConfig(externalType, uri);
    }

    @Override
    public JsonObject serialize(ExternalUriConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(EXTERNAL_TYPE_KEY, data.externalType().raw());
        builder.add(URI_KEY, data.uri().toString());

        return builder.build();
    }
}
