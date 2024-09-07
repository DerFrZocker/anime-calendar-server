package de.derfrzocker.anime.calendar.impl.layer.layer;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.layer.config.RegionStreamingUrlLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.RegionStreamingUrlLayerConfigParser;
import de.derfrzocker.anime.calendar.utils.LayerUtil;
import org.jetbrains.annotations.NotNull;

public final class RegionStreamingUrlLayer extends AbstractEpisodeTransformer<RegionStreamingUrlLayerConfig> {

    public static final RegionStreamingUrlLayer INSTANCE = new RegionStreamingUrlLayer();

    private RegionStreamingUrlLayer() {
        super("region-streaming-url", RegionStreamingUrlLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull RegionStreamingUrlLayerConfig layerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (LayerUtil.shouldSkip(episodeBuilder, layerConfig)) {
            return;
        }

        if (!layerConfig.getApplicableRegions().isEmpty() && !layerConfig.getApplicableRegions().contains(animeOptions.region())) {
            return;
        }

        episodeBuilder.withStreamingLink(layerConfig.getUrl());
    }
}