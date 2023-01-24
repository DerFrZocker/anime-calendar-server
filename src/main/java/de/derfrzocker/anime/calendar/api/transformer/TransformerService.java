package de.derfrzocker.anime.calendar.api.transformer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.Episode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface TransformerService {

    void registerEpisodeTransformer(@NotNull EpisodeTransformer<?> episodeTransformer);

    @Nullable
    EpisodeTransformer<?> getEpisodeTransformer(@NotNull String transformerKey);

    @NotNull
    List<Episode> transformAnime(@NotNull Anime anime, @NotNull AnimeOptions animeOptions);
}
