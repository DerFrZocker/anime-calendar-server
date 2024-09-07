package de.derfrzocker.anime.calendar.impl.layer.layer;

import com.spencerwi.either.Either;
import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.layer.config.SimpleIntegerLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.SimpleIntegerLayerConfigParser;
import de.derfrzocker.anime.calendar.utils.LayerUtil;
import org.jetbrains.annotations.NotNull;

public final class EpisodeNumberLayer extends AbstractEpisodeTransformer<SimpleIntegerLayerConfig> {

    public static final EpisodeNumberLayer INSTANCE = new EpisodeNumberLayer();

    private EpisodeNumberLayer() {
        super("episode-number", SimpleIntegerLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull SimpleIntegerLayerConfig layerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (LayerUtil.shouldSkip(episodeBuilder, layerConfig)) {
            return;
        }

        episodeBuilder.withEpisodeNumber(Either.right((episodeBuilder.episodeIndex() - layerConfig.getMinInclusive()) + layerConfig.getValue()));
    }
}
