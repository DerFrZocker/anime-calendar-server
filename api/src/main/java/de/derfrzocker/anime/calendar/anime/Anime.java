package de.derfrzocker.anime.calendar.anime;

import de.derfrzocker.anime.calendar.metadata.SimpleMetadataHolder;

import java.util.List;

public interface Anime extends SimpleMetadataHolder {

    AnimeId id();

    String name();

    int episodeCount();

    List<LayerStepConfig> episodeLayers();
}
