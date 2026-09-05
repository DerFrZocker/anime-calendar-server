package de.derfrzocker.anime.calendar.layer.api;

import de.derfrzocker.anime.calendar.anime.api.Anime;
import de.derfrzocker.anime.calendar.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.episode.api.EpisodeBuilder;

public interface LayerFilter<T extends LayerConfig> {

    boolean shouldSkipUnsafe(Anime anime,
                             AnimeOptions animeOptions,
                             LayerConfig layerConfig,
                             EpisodeBuilder episodeBuilder);

    boolean shouldSkip(Anime anime, AnimeOptions animeOptions, T layerConfig, EpisodeBuilder episodeBuilder);
}
