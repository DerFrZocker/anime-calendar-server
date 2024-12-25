package de.derfrzocker.anime.calendar.server.layer.filter;

import de.derfrzocker.anime.calendar.server.layer.config.BoundFilterConfig;
import de.derfrzocker.anime.calendar.server.layer.parser.BoundConfigLayerParser;
import de.derfrzocker.anime.calendar.server.model.domain.ical.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.ical.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;

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
