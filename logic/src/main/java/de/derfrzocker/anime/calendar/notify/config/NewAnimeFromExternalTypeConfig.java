package de.derfrzocker.anime.calendar.notify.config;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.notify.NotifyData;
import de.derfrzocker.anime.calendar.notify.NotifyDataType;

import java.util.Objects;

public record NewAnimeFromExternalTypeConfig(ExternalType externalType, ExternalAnimeId externalAnimeId, String name)
        implements NotifyData {

    public static final String DATA_TYE_RAW = "new_anime_from_external_type";
    public static final NotifyDataType DATA_TYE = NotifyDataType.of(DATA_TYE_RAW);

    public NewAnimeFromExternalTypeConfig {
        Objects.requireNonNull(externalType);
        Objects.requireNonNull(externalAnimeId);
        Objects.requireNonNull(name);
    }
}
