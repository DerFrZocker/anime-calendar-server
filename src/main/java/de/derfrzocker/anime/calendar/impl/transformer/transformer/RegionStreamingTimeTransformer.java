package de.derfrzocker.anime.calendar.impl.transformer.transformer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.transformer.config.RegionStreamingTimeTransformerConfig;
import de.derfrzocker.anime.calendar.impl.transformer.parser.RegionStreamingTimeConfigParser;
import de.derfrzocker.anime.calendar.utils.TransformerUtil;
import org.jetbrains.annotations.NotNull;

import java.time.Period;

public final class RegionStreamingTimeTransformer extends AbstractEpisodeTransformer<RegionStreamingTimeTransformerConfig> {


    public static final RegionStreamingTimeTransformer INSTANCE = new RegionStreamingTimeTransformer();

    private RegionStreamingTimeTransformer() {
        super("region-streaming-time", RegionStreamingTimeConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull RegionStreamingTimeTransformerConfig transformerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (TransformerUtil.shouldSkip(episodeBuilder, transformerConfig)) {
            return;
        }

        if (!transformerConfig.getApplicableRegions().isEmpty() && !transformerConfig.getApplicableRegions().contains(animeOptions.region())) {
            return;
        }

        Period period = transformerConfig.getPeriod().multipliedBy((episodeBuilder.episodeIndex() - transformerConfig.getMinInclusive()));

        episodeBuilder.withStreamingTime(transformerConfig.getStartTime().plus(period));
        episodeBuilder.withType(transformerConfig.getType());
    }
}
