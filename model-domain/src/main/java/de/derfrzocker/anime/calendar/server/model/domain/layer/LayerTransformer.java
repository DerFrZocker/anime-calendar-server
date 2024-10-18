package de.derfrzocker.anime.calendar.server.model.domain.layer;

import de.derfrzocker.anime.calendar.server.model.domain.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import org.jetbrains.annotations.NotNull;

public interface LayerTransformer<T extends LayerConfig> extends Layer<T, LayerTransformerDataHolder<T>> {

    void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull T layerConfig, @NotNull EpisodeBuilder episodeBuilder);
}
