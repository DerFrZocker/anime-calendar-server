package de.derfrzocker.anime.calendar.anime.service;

import de.derfrzocker.anime.calendar.anime.Anime;
import de.derfrzocker.anime.calendar.anime.Episode;
import de.derfrzocker.anime.calendar.anime.EpisodeBuildOption;
import de.derfrzocker.anime.calendar.anime.EpisodeBuilder;
import de.derfrzocker.anime.calendar.anime.LayerConfig;
import de.derfrzocker.anime.calendar.anime.LayerConfigData;
import de.derfrzocker.anime.calendar.anime.LayerFilter;
import de.derfrzocker.anime.calendar.anime.LayerStepConfig;
import de.derfrzocker.anime.calendar.anime.LayerTransformer;
import de.derfrzocker.anime.calendar.anime.LayerType;
import de.derfrzocker.anime.calendar.anime.model.EpisodeBuilderImpl;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EpisodeBuildService {

    private final Instance<LayerFilter<?>> layerFilters;
    private final Instance<LayerFilter<?>> layerTransformers;

    @Inject
    EpisodeBuildService(Instance<LayerFilter<?>> layerFilters, Instance<LayerFilter<?>> layerTransformers) {
        this.layerFilters = layerFilters;
        this.layerTransformers = layerTransformers;
    }

    public List<Episode> buildEpisodes(Anime anime, EpisodeBuildOption buildOption) {
        List<Episode> episodes = new ArrayList<>();

        for (int i = 0; i < anime.episodeCount(); i++) {
            buildEpisode(anime, buildOption, i).ifPresent(episodes::add);
        }

        return episodes;
    }

    public Optional<Episode> buildEpisode(Anime anime, EpisodeBuildOption buildOption, int episodeIndex) {
        if (episodeIndex < 0 || episodeIndex >= anime.episodeCount()) {
            throw new IllegalArgumentException("Episode index '%s' must be between '0' and '%s'".formatted(
                    episodeIndex,
                    anime.episodeCount()));
        }

        EpisodeBuilder builder = EpisodeBuilderImpl.of(episodeIndex, anime.id(), anime.name());

        for (LayerStepConfig stepConfig : anime.episodeLayers()) {
            if (shouldSkip(anime, buildOption, builder, stepConfig)) {
                continue;
            }

            transform(anime, buildOption, builder, stepConfig.transformer());
        }

        return builder.buildIfValid();
    }

    private boolean shouldSkip(
            Anime anime,
            EpisodeBuildOption buildOption,
            EpisodeBuilder builder,
            LayerStepConfig stepConfig) {

        for (LayerConfig<?> layerConfig : stepConfig.filters()) {
            if (shouldSkip(anime, buildOption, builder, layerConfig)) {
                return true;
            }
        }

        return false;
    }

    private <T extends LayerConfigData> void transform(
            Anime anime,
            EpisodeBuildOption buildOption,
            EpisodeBuilder builder,
            LayerConfig<T> config) {

        layerTransformer(config.type()).transform(anime, buildOption, builder, config.data());
    }

    private <T extends LayerConfigData> boolean shouldSkip(
            Anime anime,
            EpisodeBuildOption buildOption,
            EpisodeBuilder builder,
            LayerConfig<T> config) {

        return layerFilter(config.type()).shouldSkip(anime, buildOption, builder, config.data());
    }

    @SuppressWarnings("unchecked")
    private <T extends LayerConfigData> LayerTransformer<T> layerTransformer(LayerType type) {
        return (LayerTransformer<T>) layerTransformers.select(Identifier.Literal.of(type.raw())).get();
    }

    @SuppressWarnings("unchecked")
    private <T extends LayerConfigData> LayerFilter<T> layerFilter(LayerType type) {
        return (LayerFilter<T>) layerFilters.select(Identifier.Literal.of(type.raw())).get();
    }
}
