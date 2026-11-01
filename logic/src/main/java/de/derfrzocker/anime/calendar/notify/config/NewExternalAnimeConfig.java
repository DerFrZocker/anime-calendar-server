package de.derfrzocker.anime.calendar.notify.config;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.notify.NotifyData;
import de.derfrzocker.anime.calendar.notify.NotifyDataType;
import java.util.Objects;

public record NewExternalAnimeConfig(ExternalType externalType, ExternalAnimeId externalAnimeId, String name)
        implements NotifyData {

    public static final String DATA_TYPE_RAW = "new_external_anime";
    public static final NotifyDataType DATA_TYPE = NotifyDataType.of(DATA_TYPE_RAW);

    public NewExternalAnimeConfig {
        Objects.requireNonNull(externalType);
        Objects.requireNonNull(externalAnimeId);
        Objects.requireNonNull(name);
    }
}
