package de.derfrzocker.anime.calendar.server.anime.api.layer;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;

public interface LayerFilter<T extends LayerConfig> extends Layer<T, LayerFilterDataHolder<T>> {

    boolean shouldSkip(Anime anime, AnimeOptions animeOptions, T layerConfig, EpisodeBuilder episodeBuilder);
}
