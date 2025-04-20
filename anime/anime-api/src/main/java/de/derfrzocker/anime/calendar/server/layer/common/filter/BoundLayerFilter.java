package de.derfrzocker.anime.calendar.server.layer.common.filter;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer.api.AbstractLayerFilter;
import de.derfrzocker.anime.calendar.server.layer.common.config.BoundFilterConfig;

public final class BoundLayerFilter extends AbstractLayerFilter<BoundFilterConfig> {

    public static final LayerKey LAYER_KEY = new LayerKey("bound-filter");
    public static final BoundLayerFilter INSTANCE = new BoundLayerFilter();

    private BoundLayerFilter() {
    }

    @Override
    public boolean shouldSkip(Anime anime,
                              AnimeOptions animeOptions,
                              BoundFilterConfig layerConfig,
                              EpisodeBuilder episodeBuilder) {
        if (episodeBuilder.episodeIndex() < layerConfig.minInclusive()) {
            return true;
        }

        return layerConfig.maxInclusive() != -1 && episodeBuilder.episodeIndex() > layerConfig.maxInclusive();
    }

    @Override
    protected Class<BoundFilterConfig> configClass() {
        return BoundFilterConfig.class;
    }
}
