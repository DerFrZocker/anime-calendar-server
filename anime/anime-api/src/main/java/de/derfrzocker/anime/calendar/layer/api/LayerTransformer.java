package de.derfrzocker.anime.calendar.layer.api;

import de.derfrzocker.anime.calendar.anime.api.Anime;
import de.derfrzocker.anime.calendar.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.episode.api.EpisodeBuilder;

public interface LayerTransformer<T extends LayerConfig> {

    void transformUnsafe(
            Anime anime,
            AnimeOptions animeOptions,
            LayerConfig layerConfig,
            EpisodeBuilder episodeBuilder);

    void transform(Anime anime, AnimeOptions animeOptions, T layerConfig, EpisodeBuilder episodeBuilder);
}
