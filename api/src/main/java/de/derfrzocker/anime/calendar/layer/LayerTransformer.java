package de.derfrzocker.anime.calendar.layer;

import de.derfrzocker.anime.calendar.anime.Anime;

public interface LayerTransformer<T extends LayerConfig> {

    void transform(Anime anime, AnimeOptions animeOptions, T layerConfig, EpisodeBuilder episodeBuilder);
}
