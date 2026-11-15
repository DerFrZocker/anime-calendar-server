package de.derfrzocker.anime.calendar.notify.codec;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.notify.NotifyCodec;
import de.derfrzocker.anime.calendar.notify.config.IgnoreNewAnimeFromExternalTypeActionConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Identifier(IgnoreNewAnimeFromExternalTypeActionConfig.DATA_TYE_RAW)
@ApplicationScoped
class IgnoreNewAnimeFromExternalTypeActionCodec implements NotifyCodec<IgnoreNewAnimeFromExternalTypeActionConfig> {

    private final static String EXTERNAL_TYPE_KEY = "external_type";
    private final static String EXTERNAL_ANIME_ID_KEY = "external_anime_id";

    @Override
    public IgnoreNewAnimeFromExternalTypeActionConfig deserialize(JsonObject data) {

        ExternalType externalType = ExternalType.of(data.getString(EXTERNAL_TYPE_KEY));
        ExternalAnimeId externalAnimeId = ExternalAnimeId.of(data.getString(EXTERNAL_ANIME_ID_KEY));

        return new IgnoreNewAnimeFromExternalTypeActionConfig(externalType, externalAnimeId);
    }

    @Override
    public JsonObject serialize(IgnoreNewAnimeFromExternalTypeActionConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(EXTERNAL_TYPE_KEY, data.externalType().raw());
        builder.add(EXTERNAL_ANIME_ID_KEY, data.externalAnimeId().raw());

        return builder.build();
    }
}
