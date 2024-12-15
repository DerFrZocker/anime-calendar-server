package de.derfrzocker.anime.calendar.impl.layer.filter;

import de.derfrzocker.anime.calendar.impl.layer.config.RegionFilterConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.RegionFilterLayerConfigParser;
import de.derfrzocker.anime.calendar.server.model.domain.ical.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.ical.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;

public final class RegionLayerFilter extends AbstractLayerFilter<RegionFilterConfig> {

    public static final RegionLayerFilter INSTANCE = new RegionLayerFilter();

    private RegionLayerFilter() {
        super("region-filter", RegionFilterLayerConfigParser.INSTANCE);
    }

    @Override
    public boolean shouldSkip(Anime anime, AnimeOptions animeOptions, RegionFilterConfig layerConfig, EpisodeBuilder episodeBuilder) {
        return !layerConfig.applicableRegions().contains(animeOptions.region());
    }
}
