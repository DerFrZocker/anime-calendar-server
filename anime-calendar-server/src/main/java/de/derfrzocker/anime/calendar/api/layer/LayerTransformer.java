package de.derfrzocker.anime.calendar.api.layer;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import org.jetbrains.annotations.NotNull;

public interface LayerTransformer<T extends LayerConfig> extends Layer<T, LayerTransformerDataHolder<T>> {

    void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull T layerConfig, @NotNull EpisodeBuilder episodeBuilder);
}
