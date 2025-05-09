package de.derfrzocker.anime.calendar.server.layer.api;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.EpisodeBuilder;

public interface LayerFilter<T extends LayerConfig> {

    boolean shouldSkipUnsafe(Anime anime,
                             AnimeOptions animeOptions,
                             LayerConfig layerConfig,
                             EpisodeBuilder episodeBuilder);

    boolean shouldSkip(Anime anime, AnimeOptions animeOptions, T layerConfig, EpisodeBuilder episodeBuilder);
}
