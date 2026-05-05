package de.derfrzocker.anime.calendar.server.layer.common.transformer;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer.api.AbstractLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.config.IntegrationUrlLayerConfig;
import java.util.Objects;

public final class IntegrationUrlLayerTransformer extends AbstractLayerTransformer<IntegrationUrlLayerConfig> {

    public static final LayerKey LAYER_KEY = new LayerKey("integration-url");
    public static final IntegrationUrlLayerTransformer INSTANCE = new IntegrationUrlLayerTransformer();

    private IntegrationUrlLayerTransformer() {
    }

    @Override
    public void transform(
            Anime anime,
            AnimeOptions animeOptions,
            IntegrationUrlLayerConfig layerConfig,
            EpisodeBuilder episodeBuilder) {
        if (!Objects.equals(animeOptions.integrationId(), layerConfig.integrationId())) {
            return;
        }

        episodeBuilder.withIntegrationLink(layerConfig.url());
    }

    @Override
    protected Class<IntegrationUrlLayerConfig> configClass() {
        return IntegrationUrlLayerConfig.class;
    }
}