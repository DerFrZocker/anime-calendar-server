package de.derfrzocker.anime.calendar.server.anime.api.layer;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public record LayerFilterDataHolder<T extends LayerConfig>(LayerFilter<T> filter, T layerConfig) {

    public boolean shouldSkip(@NotNull Anime anime,
                              @NotNull AnimeOptions animeOptions,
                              @NotNull EpisodeBuilder episodeBuilder) {
        return filter().shouldSkip(anime, animeOptions, layerConfig(), episodeBuilder);
    }

    public Map<String, Object> encode() {
        return filter().getLayerConfigParser().encode(layerConfig);
    }
}
