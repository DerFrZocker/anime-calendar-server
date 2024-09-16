package de.derfrzocker.anime.calendar.impl.layer.transformer;

import com.spencerwi.either.Either;
import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.layer.config.SimpleOffsetIntegerLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.SimpleOffsetIntegerLayerConfigParser;
import org.jetbrains.annotations.NotNull;

public final class EpisodeNumberLayer extends AbstractEpisodeTransformer<SimpleOffsetIntegerLayerConfig> {

    public static final EpisodeNumberLayer INSTANCE = new EpisodeNumberLayer();

    private EpisodeNumberLayer() {
        super("episode-number", SimpleOffsetIntegerLayerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull SimpleOffsetIntegerLayerConfig layerConfig, @NotNull EpisodeBuilder episodeBuilder) {

        episodeBuilder.withEpisodeNumber(Either.right((episodeBuilder.episodeIndex() - layerConfig.offset()) + layerConfig.value()));
    }
}
