package de.derfrzocker.anime.calendar.impl.layer.transformer;

import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.impl.layer.config.IntegrationUrlLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.IntegrationUrlLayerConfigParser;
import org.jetbrains.annotations.NotNull;

public final class IntegrationUrlLayer extends AbstractEpisodeTransformer<IntegrationUrlLayerConfig> {

    public static final IntegrationUrlLayer INSTANCE = new IntegrationUrlLayer();

    private IntegrationUrlLayer() {
        super("integration-url", IntegrationUrlLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull IntegrationUrlLayerConfig layerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        episodeBuilder.withIntegrationLink(layerConfig.url());
    }
}