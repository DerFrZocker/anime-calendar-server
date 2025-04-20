package de.derfrzocker.anime.calendar.server.layer.api;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.EpisodeBuilder;
import org.jetbrains.annotations.NotNull;

public interface LayerTransformer<T extends LayerConfig> {

    void transformUnsafe(@NotNull Anime anime,
                         @NotNull AnimeOptions animeOptions,
                         @NotNull LayerConfig layerConfig,
                         @NotNull EpisodeBuilder episodeBuilder);

    void transform(@NotNull Anime anime,
                   @NotNull AnimeOptions animeOptions,
                   @NotNull T layerConfig,
                   @NotNull EpisodeBuilder episodeBuilder);
}
