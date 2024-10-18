package de.derfrzocker.anime.calendar.impl.layer.filter;

import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.impl.layer.config.RegionFilterConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.RegionFilterLayerConfigParser;

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
