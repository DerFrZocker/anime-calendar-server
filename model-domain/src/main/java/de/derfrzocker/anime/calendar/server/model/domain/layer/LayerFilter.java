package de.derfrzocker.anime.calendar.server.model.domain.layer;

import de.derfrzocker.anime.calendar.server.model.domain.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;

public interface LayerFilter<T extends LayerConfig> extends Layer<T, LayerFilterDataHolder<T>> {

    boolean shouldSkip(Anime anime, AnimeOptions animeOptions, T layerConfig, EpisodeBuilder episodeBuilder);
}
