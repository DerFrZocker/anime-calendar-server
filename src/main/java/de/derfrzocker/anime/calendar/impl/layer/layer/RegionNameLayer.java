package de.derfrzocker.anime.calendar.impl.layer.layer;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.layer.config.RegionNameLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.RegionNameLayerConfigParser;
import de.derfrzocker.anime.calendar.utils.LayerUtil;
import org.jetbrains.annotations.NotNull;

public final class RegionNameLayer extends AbstractEpisodeTransformer<RegionNameLayerConfig> {

    public static final RegionNameLayer INSTANCE = new RegionNameLayer();

    private RegionNameLayer() {
        super("region-name", RegionNameLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull RegionNameLayerConfig layerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (!animeOptions.useRegionName() || LayerUtil.shouldSkip(episodeBuilder, layerConfig)) {
            return;
        }

        if (!layerConfig.getApplicableRegions().isEmpty() && !layerConfig.getApplicableRegions().contains(animeOptions.region())) {
            return;
        }

        episodeBuilder.withEpisodeName(layerConfig.getName());
    }
}
