package de.derfrzocker.anime.calendar.notify.codec;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.notify.NotifyCodec;
import de.derfrzocker.anime.calendar.notify.config.NewAnimeFromExternalTypeConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Identifier(NewAnimeFromExternalTypeConfig.DATA_TYE_RAW)
@ApplicationScoped
class NewAnimeFromExternalTypeCodec implements NotifyCodec<NewAnimeFromExternalTypeConfig> {

    private final static String EXTERNAL_TYPE_KEY = "external_type";
    private final static String EXTERNAL_ANIME_ID_KEY = "external_anime_id";
    private final static String NAME_KEY = "name";

    @Override
    public NewAnimeFromExternalTypeConfig deserialize(JsonObject data) {

        ExternalType externalType = ExternalType.of(data.getString(EXTERNAL_TYPE_KEY));
        ExternalAnimeId externalAnimeId = ExternalAnimeId.of(data.getString(EXTERNAL_ANIME_ID_KEY));
        String name = data.getString(NAME_KEY);

        return new NewAnimeFromExternalTypeConfig(externalType, externalAnimeId, name);
    }

    @Override
    public JsonObject serialize(NewAnimeFromExternalTypeConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(EXTERNAL_TYPE_KEY, data.externalType().raw());
        builder.add(EXTERNAL_ANIME_ID_KEY, data.externalAnimeId().raw());
        builder.add(NAME_KEY, data.name());

        return builder.build();
    }
}
