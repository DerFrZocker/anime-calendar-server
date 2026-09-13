package de.derfrzocker.anime.calendar.layer;

import de.derfrzocker.anime.calendar.anime.Anime;

public interface LayerFilter<T extends LayerConfig> {

    boolean shouldSkip(Anime anime, AnimeOptions animeOptions, T layerConfig, EpisodeBuilder episodeBuilder);
}
