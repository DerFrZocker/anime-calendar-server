package de.derfrzocker.anime.calendar.external;

import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.metadata.SimpleEntityMetadata;
import de.derfrzocker.anime.calendar.metadata.SimpleMetadataHolder;

import java.util.Objects;

public record ExternalAnimeLink(
        ExternalAnimeLinkId id,
        AnimeId animeId,
        ExternalType externalType,
        ExternalAnimeId externalAnimeId,
        SimpleEntityMetadata metadata) implements SimpleMetadataHolder {

    public ExternalAnimeLink {
        Objects.requireNonNull(id);
        Objects.requireNonNull(animeId);
        Objects.requireNonNull(externalType);
        Objects.requireNonNull(externalAnimeId);
    }
}
