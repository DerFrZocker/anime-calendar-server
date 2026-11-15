package de.derfrzocker.anime.calendar.notify.config;

import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.notify.NotifyData;
import de.derfrzocker.anime.calendar.notify.NotifyDataType;

import java.util.Objects;

public record LinkAnimeToExternalTypeConfig(AnimeId animeId, String name) implements NotifyData {

    public static final String DATA_TYE_RAW = "link_anime_to_external_type";
    public static final NotifyDataType DATA_TYE = NotifyDataType.of(DATA_TYE_RAW);

    public LinkAnimeToExternalTypeConfig {
        Objects.requireNonNull(animeId);
        Objects.requireNonNull(name);
    }
}
