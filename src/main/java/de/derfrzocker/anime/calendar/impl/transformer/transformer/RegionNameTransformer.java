package de.derfrzocker.anime.calendar.impl.transformer.transformer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.transformer.config.RegionNameTransformerConfig;
import de.derfrzocker.anime.calendar.impl.transformer.parser.RegionNameConfigParser;
import de.derfrzocker.anime.calendar.utils.TransformerUtil;
import org.jetbrains.annotations.NotNull;

public final class RegionNameTransformer extends AbstractEpisodeTransformer<RegionNameTransformerConfig> {

    public static final RegionNameTransformer INSTANCE = new RegionNameTransformer();

    private RegionNameTransformer() {
        super("region-name", RegionNameConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull RegionNameTransformerConfig transformerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (!animeOptions.useRegionName() || TransformerUtil.shouldSkip(episodeBuilder, transformerConfig)) {
            return;
        }

        if (!transformerConfig.getApplicableRegions().isEmpty() && !transformerConfig.getApplicableRegions().contains(animeOptions.region())) {
            return;
        }

        episodeBuilder.withEpisodeName(transformerConfig.getName());
    }
}
