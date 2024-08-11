package de.derfrzocker.anime.calendar.api.layer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public record LayerDataHolder<T extends LayerConfig>(@NotNull Layer<T> layer,
                                                     @NotNull T layerConfig) {

    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull EpisodeBuilder episodeBuilder) {
        layer().transform(anime, animeOptions, layerConfig(), episodeBuilder);
    }

    public Map<String, Object> encode() {
        return layer.getLayerConfigParser().encode(layerConfig);
    }
}