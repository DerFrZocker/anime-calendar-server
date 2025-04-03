package de.derfrzocker.anime.calendar.server.layer.transformer;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer.config.StreamingUrlLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.parser.StreamingUrlLayerConfigParser;
import org.jetbrains.annotations.NotNull;

public final class StreamingUrlLayer extends AbstractEpisodeTransformer<StreamingUrlLayerConfig> {

    public static final StreamingUrlLayer INSTANCE = new StreamingUrlLayer();

    private StreamingUrlLayer() {
        super("streaming-url", StreamingUrlLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime,
                          @NotNull AnimeOptions animeOptions,
                          @NotNull StreamingUrlLayerConfig layerConfig,
                          @NotNull EpisodeBuilder episodeBuilder) {
        episodeBuilder.withStreamingLink(layerConfig.url());
    }
}