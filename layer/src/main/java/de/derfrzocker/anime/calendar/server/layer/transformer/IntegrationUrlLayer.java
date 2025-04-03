package de.derfrzocker.anime.calendar.server.layer.transformer;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer.config.IntegrationUrlLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.parser.IntegrationUrlLayerConfigParser;
import org.jetbrains.annotations.NotNull;

public final class IntegrationUrlLayer extends AbstractEpisodeTransformer<IntegrationUrlLayerConfig> {

    public static final IntegrationUrlLayer INSTANCE = new IntegrationUrlLayer();

    private IntegrationUrlLayer() {
        super("integration-url", IntegrationUrlLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime,
                          @NotNull AnimeOptions animeOptions,
                          @NotNull IntegrationUrlLayerConfig layerConfig,
                          @NotNull EpisodeBuilder episodeBuilder) {
        episodeBuilder.withIntegrationLink(layerConfig.url());
    }
}