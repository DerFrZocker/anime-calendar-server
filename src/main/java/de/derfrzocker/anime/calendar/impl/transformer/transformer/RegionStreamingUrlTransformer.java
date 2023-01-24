package de.derfrzocker.anime.calendar.impl.transformer.transformer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.transformer.config.RegionStreamingUrlTransformerConfig;
import de.derfrzocker.anime.calendar.impl.transformer.parser.RegionStreamingUrlConfigParser;
import de.derfrzocker.anime.calendar.utils.TransformerUtil;
import org.jetbrains.annotations.NotNull;

public final class RegionStreamingUrlTransformer extends AbstractEpisodeTransformer<RegionStreamingUrlTransformerConfig> {

    public static final RegionStreamingUrlTransformer INSTANCE = new RegionStreamingUrlTransformer();

    private RegionStreamingUrlTransformer() {
        super("region-streaming-url", RegionStreamingUrlConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull RegionStreamingUrlTransformerConfig transformerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (TransformerUtil.shouldSkip(episodeBuilder, transformerConfig)) {
            return;
        }

        if (!transformerConfig.getApplicableRegions().isEmpty() && !transformerConfig.getApplicableRegions().contains(animeOptions.region())) {
            return;
        }

        episodeBuilder.withStreamingLink(transformerConfig.getUrl());
    }
}