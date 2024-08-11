package de.derfrzocker.anime.calendar.impl.layer.layer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.layer.config.BaseStreamingTimeLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.BaseStreamingTimeLayerConfigParser;
import de.derfrzocker.anime.calendar.utils.LayerUtil;
import org.jetbrains.annotations.NotNull;

import java.time.Period;

public final class BaseStreamingTimeLayer extends AbstractEpisodeTransformer<BaseStreamingTimeLayerConfig> {

    public static final BaseStreamingTimeLayer INSTANCE = new BaseStreamingTimeLayer();

    private BaseStreamingTimeLayer() {
        super("base-streaming-time", BaseStreamingTimeLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull BaseStreamingTimeLayerConfig layerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (LayerUtil.shouldSkip(episodeBuilder, layerConfig)) {
            return;
        }

        Period period = layerConfig.getPeriod().multipliedBy((episodeBuilder.episodeIndex() - layerConfig.getMinInclusive()));

        episodeBuilder.withStreamingTime(layerConfig.getStartTime().plus(period));
        episodeBuilder.withType("orig");
    }
}
