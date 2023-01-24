package de.derfrzocker.anime.calendar.impl.transformer;

import de.derfrzocker.anime.calendar.api.Anime;
import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.Episode;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.api.transformer.EpisodeTransformer;
import de.derfrzocker.anime.calendar.api.transformer.TransformerService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TransformerServiceImpl implements TransformerService {

    private final Map<String, EpisodeTransformer<?>> episodeTransformers = new HashMap<>();

    @Override
    public void registerEpisodeTransformer(@NotNull EpisodeTransformer<?> episodeTransformer) {
        Objects.requireNonNull(episodeTransformer, "Cannot register null EpisodeTransformer");

        if (episodeTransformers.containsKey(episodeTransformer.getTransformKey())) {
            throw new IllegalArgumentException("A EpisodeTransformer with the key " + episodeTransformer.getTransformKey() + " is already registered");
        }

        episodeTransformers.put(episodeTransformer.getTransformKey(), episodeTransformer);
    }

    @Override
    public @Nullable EpisodeTransformer<?> getEpisodeTransformer(@NotNull String transformerKey) {
        Objects.requireNonNull(transformerKey, "Cannot get EpisodeTransformer from null key");
        return episodeTransformers.get(transformerKey);
    }

    @Override
    public @NotNull List<@NotNull Episode> transformAnime(@NotNull Anime anime, @NotNull AnimeOptions animeOptions) {
        Objects.requireNonNull(anime, "Cannot transform null Anime");
        Objects.requireNonNull(animeOptions, "Cannot transform with out AnimeOptions");

        List<Episode> episodes = new ArrayList<>();
        for (int i = 0; i < anime.episodeCount(); i++) {
            EpisodeBuilder episodeBuilder = EpisodeBuilder.anEpisode(i);

            anime.transformerData().forEach(transformerData -> transformerData.transform(anime, animeOptions, episodeBuilder));

            episodes.add(episodeBuilder.build());
        }

        return episodes;
    }
}
