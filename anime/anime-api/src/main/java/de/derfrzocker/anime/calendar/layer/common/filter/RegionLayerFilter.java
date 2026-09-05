package de.derfrzocker.anime.calendar.layer.common.filter;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.anime.api.Anime;
import de.derfrzocker.anime.calendar.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.episode.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.layer.api.AbstractLayerFilter;
import de.derfrzocker.anime.calendar.layer.common.config.RegionFilterConfig;

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
