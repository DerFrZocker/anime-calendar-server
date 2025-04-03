package de.derfrzocker.anime.calendar.server.anime.api.layer;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import org.jetbrains.annotations.NotNull;

public interface LayerTransformer<T extends LayerConfig> extends Layer<T, LayerTransformerDataHolder<T>> {

    void transform(@NotNull Anime anime,
                   @NotNull AnimeOptions animeOptions,
                   @NotNull T layerConfig,
                   @NotNull EpisodeBuilder episodeBuilder);
}
