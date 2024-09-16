package de.derfrzocker.anime.calendar.impl.layer.transformer;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.layer.config.StreamingUrlLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.StreamingUrlLayerConfigParser;
import org.jetbrains.annotations.NotNull;

public final class StreamingUrlLayer extends AbstractEpisodeTransformer<StreamingUrlLayerConfig> {

    public static final StreamingUrlLayer INSTANCE = new StreamingUrlLayer();

    private StreamingUrlLayer() {
        super("streaming-url", StreamingUrlLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull StreamingUrlLayerConfig layerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        episodeBuilder.withStreamingLink(layerConfig.url());
    }
}