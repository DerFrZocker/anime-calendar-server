package de.derfrzocker.anime.calendar.impl.transformer.transformer;

import com.spencerwi.either.Either;
import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.transformer.config.SimpleIntegerTransformerConfig;
import de.derfrzocker.anime.calendar.impl.transformer.parser.SimpleIntegerConfigParser;
import de.derfrzocker.anime.calendar.utils.TransformerUtil;
import org.jetbrains.annotations.NotNull;

public final class EpisodeNumberTransformer extends AbstractEpisodeTransformer<SimpleIntegerTransformerConfig> {

    public static final EpisodeNumberTransformer INSTANCE = new EpisodeNumberTransformer();

    private EpisodeNumberTransformer() {
        super("episode-number", SimpleIntegerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull SimpleIntegerTransformerConfig transformerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (TransformerUtil.shouldSkip(episodeBuilder, transformerConfig)) {
            return;
        }

        episodeBuilder.withEpisodeNumber(Either.right((episodeBuilder.episodeIndex() - transformerConfig.getMinInclusive()) + transformerConfig.getValue()));
    }
}
