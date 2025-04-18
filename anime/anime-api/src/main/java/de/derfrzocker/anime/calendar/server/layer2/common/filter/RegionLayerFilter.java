package de.derfrzocker.anime.calendar.server.layer2.common.filter;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer2.api.AbstractLayerFilter;
import de.derfrzocker.anime.calendar.server.layer2.common.config.RegionFilterConfig;

public final class RegionLayerFilter extends AbstractLayerFilter<RegionFilterConfig> {

    public static final LayerKey LAYER_KEY = new LayerKey("region-filter");
    public static final RegionLayerFilter INSTANCE = new RegionLayerFilter();

    private RegionLayerFilter() {
    }

    @Override
    public boolean shouldSkip(Anime anime,
                              AnimeOptions animeOptions,
                              RegionFilterConfig layerConfig,
                              EpisodeBuilder episodeBuilder) {
        return !layerConfig.applicableRegions().contains(animeOptions.region());
    }

    @Override
    protected Class<RegionFilterConfig> configClass() {
        return RegionFilterConfig.class;
    }
}
