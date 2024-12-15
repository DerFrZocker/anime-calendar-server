package de.derfrzocker.anime.calendar.server.model.domain.layer;

import de.derfrzocker.anime.calendar.server.model.domain.ical.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.ical.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public record LayerTransformerDataHolder<T extends LayerConfig>(@NotNull LayerTransformer<T> layer,
                                                                @NotNull T layerConfig) {

    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull EpisodeBuilder episodeBuilder) {
        layer().transform(anime, animeOptions, layerConfig(), episodeBuilder);
    }

    public Map<String, Object> encode() {
        return layer.getLayerConfigParser().encode(layerConfig);
    }
}