package de.derfrzocker.anime.calendar.external.out;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalId;
import de.derfrzocker.anime.calendar.external.NameLanguage;
import de.derfrzocker.anime.calendar.external.NameType;

import java.util.List;
import java.util.Objects;

public record FetchedExternalAnimeNameHolder(
        ExternalId externalId,
        ExternalAnimeId externalAnimeId,
        NameType type,
        NameLanguage language,
        List<String> names) {

    public FetchedExternalAnimeNameHolder {
        Objects.requireNonNull(externalId);
        Objects.requireNonNull(externalAnimeId);
        Objects.requireNonNull(type);
        Objects.requireNonNull(language);
        Objects.requireNonNull(names);
    }
}
