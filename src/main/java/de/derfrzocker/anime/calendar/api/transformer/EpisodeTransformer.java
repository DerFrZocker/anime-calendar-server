package de.derfrzocker.anime.calendar.api.transformer;

import com.google.gson.JsonElement;
import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import org.jetbrains.annotations.NotNull;

public interface EpisodeTransformer<T extends TransformerConfig> {

    @NotNull
    String getTransformKey();

    @NotNull
    ConfigParser<T> getConfigParser();

    @NotNull
    TransformerData<T> createTransformerData(JsonElement jsonElement);

    void transform(@NotNull Anime anime, @NotNull AnimeOptions animeOptions, @NotNull T transformerConfig, @NotNull EpisodeBuilder episodeBuilder);
}
