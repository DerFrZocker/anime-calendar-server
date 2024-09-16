package de.derfrzocker.anime.calendar.impl.layer.filter;

import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.impl.layer.config.BoundFilterConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.BoundConfigLayerParser;

public final class BoundLayerFilter extends AbstractLayerFilter<BoundFilterConfig> {

    public static final BoundLayerFilter INSTANCE = new BoundLayerFilter();

    private BoundLayerFilter() {
        super("bound-filter", BoundConfigLayerParser.INSTANCE);
    }

    @Override
    public boolean shouldSkip(Anime anime, AnimeOptions animeOptions, BoundFilterConfig layerConfig, EpisodeBuilder episodeBuilder) {
        if (episodeBuilder.episodeIndex() < layerConfig.minInclusive()) {
            return true;
        }

        return layerConfig.maxInclusive() != -1 && episodeBuilder.episodeIndex() > layerConfig.maxInclusive();
    }
}
