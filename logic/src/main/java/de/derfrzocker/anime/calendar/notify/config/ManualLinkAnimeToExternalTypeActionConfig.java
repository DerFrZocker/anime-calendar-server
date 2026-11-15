package de.derfrzocker.anime.calendar.notify.config;

import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.notify.NotifyData;
import de.derfrzocker.anime.calendar.notify.NotifyDataType;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public record ManualLinkAnimeToExternalTypeActionConfig(
        AnimeId animeId, @Nullable ExternalType _externalType, @Nullable ExternalAnimeId _externalAnimeId)
        implements NotifyData {

    public static final String DATA_TYE_RAW = "manual_link_anime_to_external_type_action";
    public static final NotifyDataType DATA_TYE = NotifyDataType.of(DATA_TYE_RAW);

    public ManualLinkAnimeToExternalTypeActionConfig {
        Objects.requireNonNull(animeId);
    }

    public Optional<ExternalType> externalType() {
        return Optional.ofNullable(_externalType);
    }

    public Optional<ExternalAnimeId> externalAnimeId() {
        return Optional.ofNullable(_externalAnimeId);
    }
}
