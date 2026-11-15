package de.derfrzocker.anime.calendar.notify.codec;

import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.notify.NotifyCodec;
import de.derfrzocker.anime.calendar.notify.config.ManualLinkAnimeToExternalTypeActionConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.Optional;
import java.util.UUID;

@Identifier(ManualLinkAnimeToExternalTypeActionConfig.DATA_TYE_RAW)
@ApplicationScoped
class ManualLinkAnimeToExternalTypeActionCodec implements NotifyCodec<ManualLinkAnimeToExternalTypeActionConfig> {

    private final static String ANIME_ID_KEY = "anime_id";
    private final static String EXTERNAL_TYPE_KEY = "external_type";
    private final static String EXTERNAL_ANIME_ID_KEY = "external_anime_id";

    @Override
    public ManualLinkAnimeToExternalTypeActionConfig deserialize(JsonObject data) {

        AnimeId animeId = AnimeId.of(UUID.fromString(data.getString(ANIME_ID_KEY)));
        ExternalType externalType =
                Optional.ofNullable(data.getString(EXTERNAL_TYPE_KEY, null)).map(ExternalType::of).orElse(null);
        ExternalAnimeId externalAnimeId =
                Optional.ofNullable(data.getString(EXTERNAL_ANIME_ID_KEY, null)).map(ExternalAnimeId::of).orElse(null);

        return new ManualLinkAnimeToExternalTypeActionConfig(animeId, externalType, externalAnimeId);
    }

    @Override
    public JsonObject serialize(ManualLinkAnimeToExternalTypeActionConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(ANIME_ID_KEY, data.animeId().raw().toString());
        data.externalType().map(ExternalType::raw).ifPresent(raw -> builder.add(EXTERNAL_TYPE_KEY, raw));
        data.externalAnimeId().map(ExternalAnimeId::raw).ifPresent(raw -> builder.add(EXTERNAL_ANIME_ID_KEY, raw));

        return builder.build();
    }
}
