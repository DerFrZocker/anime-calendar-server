package de.derfrzocker.anime.calendar.server.episode.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.Episode;
import de.derfrzocker.anime.calendar.server.episode.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.episode.service.EpisodeBuilderService;
import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer.api.LayerStepConfig;
import de.derfrzocker.anime.calendar.server.layer.exception.LayerFilterExceptions;
import de.derfrzocker.anime.calendar.server.layer.exception.LayerTransformerExceptions;
import de.derfrzocker.anime.calendar.server.layer.service.LayerFilterService;
import de.derfrzocker.anime.calendar.server.layer.service.LayerTransformerService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EpisodeBuilderServiceImpl implements EpisodeBuilderService {

    @Inject
    LayerFilterService layerFilterService;
    @Inject
    LayerTransformerService layerTransformerService;

    @Override
    public List<Episode> buildEpisodes(Anime anime, AnimeOptions animeOptions, RequestContext context) {
        List<Episode> episodes = new ArrayList<>();

        for (int i = 0; i < anime.episodeCount(); i++) {
            episodes.add(buildEpisode(anime, animeOptions, i, context));
        }

        return episodes;
    }

    @Override
    public Episode buildEpisode(Anime anime, AnimeOptions animeOptions, int index, RequestContext context) {
        EpisodeBuilder episodeBuilder = EpisodeBuilder.anEpisode(index);
        EpisodeBuilderWorker worker = new EpisodeBuilderWorker(anime, animeOptions, episodeBuilder, context);

        anime.episodeLayers().forEach(worker::applyStepConfig);

        return episodeBuilder.build();
    }

    private class EpisodeBuilderWorker {

        private final Anime anime;
        private final AnimeOptions animeOptions;
        private final EpisodeBuilder episodeBuilder;
        private final RequestContext context;


        private EpisodeBuilderWorker(Anime anime,
                                     AnimeOptions animeOptions,
                                     EpisodeBuilder episodeBuilder,
                                     RequestContext context) {
            this.anime = anime;
            this.animeOptions = animeOptions;
            this.episodeBuilder = episodeBuilder;
            this.context = context;
        }

        public void applyStepConfig(LayerStepConfig stepConfig) {
            for (LayerConfig filterConfig : stepConfig.filterConfigs()) {
                if (shouldSkip(filterConfig)) {
                    return;
                }
            }

            applyTransformer(stepConfig.transformConfig());
        }

        private boolean shouldSkip(LayerConfig filterConfig) {
            return layerFilterService.getByKey(filterConfig.key(), this.context)
                                     .orElseThrow(LayerFilterExceptions.inconsistentNotFound(filterConfig.key()))
                                     .shouldSkipUnsafe(this.anime,
                                                       this.animeOptions,
                                                       filterConfig,
                                                       this.episodeBuilder);

        }

        private void applyTransformer(LayerConfig transformConfig) {
            layerTransformerService.getByKey(transformConfig.key(), context)
                                   .orElseThrow(LayerTransformerExceptions.inconsistentNotFound(transformConfig.key()))
                                   .transformUnsafe(this.anime,
                                                    this.animeOptions,
                                                    transformConfig,
                                                    this.episodeBuilder);
        }
    }
}
