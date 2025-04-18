package de.derfrzocker.anime.calendar.server.layer2.api;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;

public abstract class AbstractLayerTransformer<T extends LayerConfig> implements LayerTransformer<T> {

    @Override
    public void transformUnsafe(Anime anime,
                                AnimeOptions animeOptions,
                                LayerConfig layerConfig,
                                EpisodeBuilder episodeBuilder) {
        if (!configClass().isInstance(layerConfig)) {
            // TODO 2025-04-12: Better exception
            throw new ClassCastException(layerConfig.getClass().getName());
        }

        transform(anime, animeOptions, configClass().cast(layerConfig), episodeBuilder);
    }

    protected abstract Class<T> configClass();
}
