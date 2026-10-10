package de.derfrzocker.anime.calendar.anime.mongodb.model;

import de.derfrzocker.anime.calendar.anime.Anime;
import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.anime.LayerStepConfig;
import de.derfrzocker.anime.calendar.metadata.SimpleEntityMetadata;

import java.util.List;
import java.util.Objects;

public record AnimeImpl(
        AnimeId id, String name, int episodeCount, List<LayerStepConfig> episodeLayers, SimpleEntityMetadata metadata)
        implements Anime {

    public AnimeImpl {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        episodeLayers = List.copyOf(Objects.requireNonNull(episodeLayers));
        Objects.requireNonNull(metadata);
    }
}
