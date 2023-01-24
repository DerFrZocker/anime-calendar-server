package de.derfrzocker.anime.calendar.impl.transformer.transformer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.transformer.config.BaseStreamingTimeTransformerConfig;
import de.derfrzocker.anime.calendar.impl.transformer.parser.BaseStreamingTimeConfigParser;
import de.derfrzocker.anime.calendar.utils.TransformerUtil;
import org.jetbrains.annotations.NotNull;

import java.time.Period;

public final class BaseStreamingTimeTransformer extends AbstractEpisodeTransformer<BaseStreamingTimeTransformerConfig> {


    public static final BaseStreamingTimeTransformer INSTANCE = new BaseStreamingTimeTransformer();

    private BaseStreamingTimeTransformer() {
        super("base-streaming-time", BaseStreamingTimeConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull BaseStreamingTimeTransformerConfig transformerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (TransformerUtil.shouldSkip(episodeBuilder, transformerConfig)) {
            return;
        }

        Period period = transformerConfig.getPeriod().multipliedBy((episodeBuilder.episodeIndex() - transformerConfig.getMinInclusive()));

        episodeBuilder.withStreamingTime(transformerConfig.getStartTime().plus(period));
        episodeBuilder.withType("orig");
    }
}
