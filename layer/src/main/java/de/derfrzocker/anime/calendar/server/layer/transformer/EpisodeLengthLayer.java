package de.derfrzocker.anime.calendar.server.layer.transformer;

import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.layer.config.SimpleIntegerLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.parser.SimpleIntegerLayerConfigParser;
import org.jetbrains.annotations.NotNull;

public final class EpisodeLengthLayer extends AbstractEpisodeTransformer<SimpleIntegerLayerConfig> {

    public static final EpisodeLengthLayer INSTANCE = new EpisodeLengthLayer();

    private EpisodeLengthLayer() {
        super("episode-length", SimpleIntegerLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime,
                          @NotNull AnimeOptions animeOptions,
                          @NotNull SimpleIntegerLayerConfig layerConfig,
                          @NotNull EpisodeBuilder episodeBuilder) {
        episodeBuilder.withEpisodeLength(layerConfig.value());
    }
}
