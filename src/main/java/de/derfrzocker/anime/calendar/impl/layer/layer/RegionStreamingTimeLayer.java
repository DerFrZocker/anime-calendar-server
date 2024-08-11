package de.derfrzocker.anime.calendar.impl.layer.layer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.layer.config.RegionStreamingTimeLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.RegionStreamingTimeLayerConfigParser;
import de.derfrzocker.anime.calendar.utils.LayerUtil;
import org.jetbrains.annotations.NotNull;

import java.time.Period;

public final class RegionStreamingTimeLayer extends AbstractEpisodeTransformer<RegionStreamingTimeLayerConfig> {

    public static final RegionStreamingTimeLayer INSTANCE = new RegionStreamingTimeLayer();

    private RegionStreamingTimeLayer() {
        super("region-streaming-time", RegionStreamingTimeLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull RegionStreamingTimeLayerConfig layerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (LayerUtil.shouldSkip(episodeBuilder, layerConfig)) {
            return;
        }

        if (!layerConfig.getApplicableRegions().isEmpty() && !layerConfig.getApplicableRegions().contains(animeOptions.region())) {
            return;
        }

        Period period = layerConfig.getPeriod().multipliedBy((episodeBuilder.episodeIndex() - layerConfig.getMinInclusive()));

        episodeBuilder.withStreamingTime(layerConfig.getStartTime().plus(period));
        episodeBuilder.withType(layerConfig.getType());
    }
}
