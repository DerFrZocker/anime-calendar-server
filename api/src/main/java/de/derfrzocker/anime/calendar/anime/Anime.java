package de.derfrzocker.anime.calendar.anime;

import de.derfrzocker.anime.calendar.metadata.SimpleEntityMetadata;
import de.derfrzocker.anime.calendar.metadata.SimpleMetadataHolder;

public record Anime(AnimeId id, String name, int episodeCount, SimpleEntityMetadata metadata)
        implements SimpleMetadataHolder {

}
