package de.derfrzocker.anime.calendar.server.layer.common.transformer;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer.api.AbstractLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.config.StreamingUrlLayerConfig;

public final class StreamingUrlLayerTransformer extends AbstractLayerTransformer<StreamingUrlLayerConfig> {

    public static final LayerKey LAYER_KEY = new LayerKey("streaming-url");
    public static final StreamingUrlLayerTransformer INSTANCE = new StreamingUrlLayerTransformer();

    private StreamingUrlLayerTransformer() {
    }

    @Override
    public void transform(
            Anime anime,
            AnimeOptions animeOptions,
            StreamingUrlLayerConfig layerConfig,
            EpisodeBuilder episodeBuilder) {
        episodeBuilder.withStreamingLink(layerConfig.url());
    }

    @Override
    protected Class<StreamingUrlLayerConfig> configClass() {
        return StreamingUrlLayerConfig.class;
    }
}