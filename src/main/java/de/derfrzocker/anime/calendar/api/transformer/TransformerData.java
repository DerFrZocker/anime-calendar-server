package de.derfrzocker.anime.calendar.api.transformer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import org.jetbrains.annotations.NotNull;

public record TransformerData<T extends TransformerConfig>(@NotNull EpisodeTransformer<T> episodeTransformer,
                                                           @NotNull T transformerConfig) {

    public void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull EpisodeBuilder episodeBuilder) {
        episodeTransformer().transform(anime, animeOptions, transformerConfig(), episodeBuilder);
    }
}
