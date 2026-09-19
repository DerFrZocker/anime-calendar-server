package de.derfrzocker.anime.calendar.anime;

import de.derfrzocker.anime.calendar.metadata.SimpleEntityMetadata;
import de.derfrzocker.anime.calendar.metadata.SimpleMetadataHolder;

import java.util.List;
import java.util.Objects;

public record Anime(
        AnimeId id, String name, int episodeCount, List<LayerStepConfig> episodeLayers, SimpleEntityMetadata metadata)
        implements SimpleMetadataHolder {

    public Anime {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(episodeLayers);
        Objects.requireNonNull(metadata);
    }
}
