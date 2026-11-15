package de.derfrzocker.anime.calendar.notify.config;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.notify.NotifyData;
import de.derfrzocker.anime.calendar.notify.NotifyDataType;

import java.util.Objects;

public record IgnoreNewAnimeFromExternalTypeActionConfig(ExternalType externalType, ExternalAnimeId externalAnimeId)
        implements NotifyData {

    public static final String DATA_TYE_RAW = "ignore_new_anime_from_external_type_action";
    public static final NotifyDataType DATA_TYE = NotifyDataType.of(DATA_TYE_RAW);

    public IgnoreNewAnimeFromExternalTypeActionConfig {
        Objects.requireNonNull(externalType);
        Objects.requireNonNull(externalAnimeId);
    }
}
