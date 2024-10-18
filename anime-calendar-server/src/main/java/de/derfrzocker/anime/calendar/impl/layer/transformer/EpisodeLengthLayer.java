package de.derfrzocker.anime.calendar.impl.layer.transformer;

import de.derfrzocker.anime.calendar.impl.layer.config.SimpleIntegerLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.SimpleIntegerLayerConfigParser;
import de.derfrzocker.anime.calendar.server.model.domain.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import org.jetbrains.annotations.NotNull;

public final class EpisodeLengthLayer extends AbstractEpisodeTransformer<SimpleIntegerLayerConfig> {

    public static final EpisodeLengthLayer INSTANCE = new EpisodeLengthLayer();

    private EpisodeLengthLayer() {
        super("episode-length", SimpleIntegerLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull SimpleIntegerLayerConfig layerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        episodeBuilder.withEpisodeLength(layerConfig.value());
    }
}
