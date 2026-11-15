package de.derfrzocker.anime.calendar.notify.codec;

import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.notify.NotifyCodec;
import de.derfrzocker.anime.calendar.notify.config.LinkAnimeToExternalTypeConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.UUID;

@Identifier(LinkAnimeToExternalTypeConfig.DATA_TYE_RAW)
@ApplicationScoped
class LinkAnimeToExternalTypeCodec implements NotifyCodec<LinkAnimeToExternalTypeConfig> {

    private final static String ANIME_ID_KEY = "anime_id";
    private final static String NAME_KEY = "name";

    @Override
    public LinkAnimeToExternalTypeConfig deserialize(JsonObject data) {

        AnimeId animeId = AnimeId.of(UUID.fromString(data.getString(ANIME_ID_KEY)));
        String name = data.getString(NAME_KEY);

        return new LinkAnimeToExternalTypeConfig(animeId, name);
    }

    @Override
    public JsonObject serialize(LinkAnimeToExternalTypeConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(ANIME_ID_KEY, data.animeId().raw().toString());
        builder.add(NAME_KEY, data.name());

        return builder.build();
    }
}
