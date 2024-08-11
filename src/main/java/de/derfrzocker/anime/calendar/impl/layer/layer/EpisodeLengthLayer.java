package de.derfrzocker.anime.calendar.impl.layer.layer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.layer.config.SimpleIntegerLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.SimpleIntegerLayerConfigParser;
import de.derfrzocker.anime.calendar.utils.LayerUtil;
import org.jetbrains.annotations.NotNull;

public final class EpisodeLengthLayer extends AbstractEpisodeTransformer<SimpleIntegerLayerConfig> {

    public static final EpisodeLengthLayer INSTANCE = new EpisodeLengthLayer();

    private EpisodeLengthLayer() {
        super("episode-length", SimpleIntegerLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull SimpleIntegerLayerConfig layerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (LayerUtil.shouldSkip(episodeBuilder, layerConfig)) {
            return;
        }

        episodeBuilder.withEpisodeLength(layerConfig.getValue());
    }
}
