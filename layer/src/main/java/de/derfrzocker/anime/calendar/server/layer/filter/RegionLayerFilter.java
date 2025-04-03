package de.derfrzocker.anime.calendar.server.layer.filter;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer.config.RegionFilterConfig;
import de.derfrzocker.anime.calendar.server.layer.parser.RegionFilterLayerConfigParser;

public final class RegionLayerFilter extends AbstractLayerFilter<RegionFilterConfig> {

    public static final RegionLayerFilter INSTANCE = new RegionLayerFilter();

    private RegionLayerFilter() {
        super("region-filter", RegionFilterLayerConfigParser.INSTANCE);
    }

    @Override
    public boolean shouldSkip(Anime anime,
                              AnimeOptions animeOptions,
                              RegionFilterConfig layerConfig,
                              EpisodeBuilder episodeBuilder) {
        return !layerConfig.applicableRegions().contains(animeOptions.region());
    }
}
