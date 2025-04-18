package de.derfrzocker.anime.calendar.server.layer2.common.transformer;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer2.api.AbstractLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer2.common.config.IntegrationUrlLayerConfig;
import org.jetbrains.annotations.NotNull;

public final class IntegrationUrlLayerTransformer extends AbstractLayerTransformer<IntegrationUrlLayerConfig> {

    public static final LayerKey LAYER_KEY = new LayerKey("integration-url");
    public static final IntegrationUrlLayerTransformer INSTANCE = new IntegrationUrlLayerTransformer();

    private IntegrationUrlLayerTransformer() {
    }

    @Override
    public void transform(@NotNull Anime anime,
                          @NotNull AnimeOptions animeOptions,
                          @NotNull IntegrationUrlLayerConfig layerConfig,
                          @NotNull EpisodeBuilder episodeBuilder) {
        episodeBuilder.withIntegrationLink(layerConfig.url());
    }

    @Override
    protected Class<IntegrationUrlLayerConfig> configClass() {
        return IntegrationUrlLayerConfig.class;
    }
}