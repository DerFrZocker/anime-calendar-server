package de.derfrzocker.anime.calendar.notify.config;

import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.notify.NotifyData;
import de.derfrzocker.anime.calendar.notify.NotifyDataType;

import java.util.Objects;

public record LinkAnimeToExternalTypeActionConfig(
        AnimeId animeId, ExternalType externalType, ExternalAnimeId externalAnimeId) implements NotifyData {

    public static final String DATA_TYE_RAW = "link_anime_to_external_type_action";
    public static final NotifyDataType DATA_TYE = NotifyDataType.of(DATA_TYE_RAW);

    public LinkAnimeToExternalTypeActionConfig {
        Objects.requireNonNull(animeId);
        Objects.requireNonNull(externalType);
        Objects.requireNonNull(externalAnimeId);
    }
}
