package de.derfrzocker.anime.calendar.external;

import java.util.List;
import java.util.Objects;

public record ExternalAnimeNameCreateData(
        ExternalId externalId,
        ExternalAnimeId externalAnimeId,
        NameType type,
        NameLanguage language,
        List<String> names) {

    public ExternalAnimeNameCreateData {
        Objects.requireNonNull(externalId);
        Objects.requireNonNull(externalAnimeId);
        Objects.requireNonNull(type);
        Objects.requireNonNull(language);
        Objects.requireNonNull(names);
    }
}
