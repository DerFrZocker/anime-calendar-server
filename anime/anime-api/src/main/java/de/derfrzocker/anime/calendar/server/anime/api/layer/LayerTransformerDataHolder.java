package de.derfrzocker.anime.calendar.server.anime.api.layer;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public record LayerTransformerDataHolder<T extends LayerConfig>(@NotNull LayerTransformer<T> layer,
                                                                @NotNull T layerConfig) {

    public void transform(@NotNull Anime anime,
                          @NotNull AnimeOptions animeOptions,
                          @NotNull EpisodeBuilder episodeBuilder) {
        layer().transform(anime, animeOptions, layerConfig(), episodeBuilder);
    }

    public Map<String, Object> encode() {
        return layer.getLayerConfigParser().encode(layerConfig);
    }
}