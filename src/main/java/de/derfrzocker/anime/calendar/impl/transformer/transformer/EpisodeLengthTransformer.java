package de.derfrzocker.anime.calendar.impl.transformer.transformer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.impl.transformer.config.SimpleIntegerTransformerConfig;
import de.derfrzocker.anime.calendar.impl.transformer.parser.SimpleIntegerConfigParser;
import de.derfrzocker.anime.calendar.utils.TransformerUtil;
import org.jetbrains.annotations.NotNull;

public final class EpisodeLengthTransformer extends AbstractEpisodeTransformer<SimpleIntegerTransformerConfig> {

    public static final EpisodeLengthTransformer INSTANCE = new EpisodeLengthTransformer();

    private EpisodeLengthTransformer() {
        super("episode-length", SimpleIntegerConfigParser.INSTANCE);
    }

    @Override
    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull SimpleIntegerTransformerConfig transformerConfig, @NotNull EpisodeBuilder episodeBuilder) {
        if (TransformerUtil.shouldSkip(episodeBuilder, transformerConfig)) {
            return;
        }

        episodeBuilder.withEpisodeLength(transformerConfig.getValue());
    }
}
