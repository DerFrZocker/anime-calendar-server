package de.derfrzocker.anime.calendar.api.layer;

import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.api.anime.Anime;

public interface LayerFilter<T extends LayerConfig> extends Layer<T, LayerFilterDataHolder<T>> {

    boolean shouldSkip(Anime anime, AnimeOptions animeOptions, T layerConfig, EpisodeBuilder episodeBuilder);
}
