package de.derfrzocker.anime.calendar.notify.codec;

import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.notify.NotifyCodec;
import de.derfrzocker.anime.calendar.notify.config.LinkAnimeToExternalTypeActionConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.UUID;

@Identifier(LinkAnimeToExternalTypeActionConfig.DATA_TYE_RAW)
@ApplicationScoped
class LinkAnimeToExternalTypeActionCodec implements NotifyCodec<LinkAnimeToExternalTypeActionConfig> {

    private final static String ANIME_ID_KEY = "anime_id";
    private final static String EXTERNAL_TYPE_KEY = "external_type";
    private final static String EXTERNAL_ANIME_ID_KEY = "external_anime_id";

    @Override
    public LinkAnimeToExternalTypeActionConfig deserialize(JsonObject data) {

        AnimeId animeId = AnimeId.of(UUID.fromString(data.getString(ANIME_ID_KEY)));
        ExternalType externalType = ExternalType.of(data.getString(EXTERNAL_TYPE_KEY));
        ExternalAnimeId externalAnimeId = ExternalAnimeId.of(data.getString(EXTERNAL_ANIME_ID_KEY));

        return new LinkAnimeToExternalTypeActionConfig(animeId, externalType, externalAnimeId);
    }

    @Override
    public JsonObject serialize(LinkAnimeToExternalTypeActionConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(ANIME_ID_KEY, data.animeId().raw().toString());
        builder.add(EXTERNAL_TYPE_KEY, data.externalType().raw());
        builder.add(EXTERNAL_ANIME_ID_KEY, data.externalAnimeId().raw());

        return builder.build();
    }
}
