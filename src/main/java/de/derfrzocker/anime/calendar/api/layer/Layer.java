package de.derfrzocker.anime.calendar.api.layer;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface Layer<T extends LayerConfig> {

    @NotNull
    LayerKey getLayerKey();

    @NotNull
    LayerConfigParser<T> getLayerConfigParser();

    @NotNull
    LayerDataHolder<T> createLayerDataHolder(Map<String, Object> values);

    void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull T layerConfig, @NotNull EpisodeBuilder episodeBuilder);
}
