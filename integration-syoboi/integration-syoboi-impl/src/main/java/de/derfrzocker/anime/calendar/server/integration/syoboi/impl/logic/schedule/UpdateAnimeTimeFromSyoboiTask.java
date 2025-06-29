package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.logic.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.anime.api.Region;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.server.episode.api.Episode;
import de.derfrzocker.anime.calendar.server.episode.api.StreamType;
import de.derfrzocker.anime.calendar.server.episode.service.EpisodeBuilderService;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import de.derfrzocker.anime.calendar.server.layer.api.LayerStepConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.BoundFilterConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.SimpleIntegerLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.SimpleOffsetIntegerLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.StreamingTimeLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.filter.BoundLayerFilter;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.EpisodeLengthLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.EpisodeNumberLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.StreamingTimeLayerTransformer;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Duration;
import java.time.Period;
import java.util.List;

@ApplicationScoped
class UpdateAnimeTimeFromSyoboiTask {

    @Inject
    AnimeService animeService;
    @Inject
    EpisodeBuilderService episodeBuilderService;

    Uni<Void> executeAsync(AnimeScheduleHolder holder, RequestContext context) {
        return Multi.createFrom()
                    .iterable(holder.links())
                    .onItem()
                    .invoke(link -> handleSinglePresentLink(link, holder, context))
                    .collect()
                    .asList()
                    .replaceWithVoid();

    }

    private void handleSinglePresentLink(AnimeIntegrationLink link, AnimeScheduleHolder data, RequestContext context) {
        Anime anime = this.animeService.getById(link.animeId(), context).orElseThrow();

        if (data.schedule().episode() > anime.episodeCount()) {
            anime = this.animeService.updateWithData(anime.id(),
                                                     AnimeUpdateData.toEpisode(data.schedule().episode()),
                                                     context);
        }

        Episode episode = getEpisode(anime, data, StreamType.ORG, context);

        anime = checkBaseStreamTime(anime, episode, data, context);
        anime = checkLength(anime, episode, data, context);
        anime = checkEpisodeCount(anime, episode, data, context);
    }

    private Anime checkBaseStreamTime(Anime anime, Episode episode, AnimeScheduleHolder data, RequestContext context) {
        if (isCorrectTime(episode, data)) {
            return anime;
        }

        // TODO 2024-12-30: Handle multiple streaming services, broadcasting the same anime.
        // TODO 2024-12-30: Make this way better
        Episode subEpisode = getEpisode(anime, data, StreamType.SUB, context);
        if (subEpisode.streamingTime() != null && episode.streamingTime() != null) {
            Duration diff = Duration.between(episode.streamingTime(), subEpisode.streamingTime());

            BoundFilterConfig filterConfig = new BoundFilterConfig(BoundLayerFilter.LAYER_KEY,
                                                                   data.schedule().episode() - 1,
                                                                   -1);
            StreamingTimeLayerConfig layerConfig = new StreamingTimeLayerConfig(StreamingTimeLayerTransformer.LAYER_KEY,
                                                                                data.schedule().startTime().plus(diff),
                                                                                Period.ofDays(7),
                                                                                data.schedule().episode() - 1,
                                                                                StreamType.SUB);
            LayerStepConfig config = new LayerStepConfig(List.of(filterConfig), layerConfig);

            anime = this.animeService.updateWithData(anime.id(), AnimeUpdateData.addingLayer(config), context);
        }

        BoundFilterConfig filterConfig = new BoundFilterConfig(BoundLayerFilter.LAYER_KEY,
                                                               data.schedule().episode() - 1,
                                                               -1);
        StreamingTimeLayerConfig layerConfig = new StreamingTimeLayerConfig(StreamingTimeLayerTransformer.LAYER_KEY,
                                                                            data.schedule().startTime(),
                                                                            Period.ofDays(7),
                                                                            data.schedule().episode() - 1,
                                                                            StreamType.ORG);
        LayerStepConfig config = new LayerStepConfig(List.of(filterConfig), layerConfig);

        return this.animeService.updateWithData(anime.id(), AnimeUpdateData.addingLayer(config), context);
    }

    private Anime checkLength(Anime anime, Episode episode, AnimeScheduleHolder data, RequestContext context) {
        long duration = Duration.between(data.schedule().startTime(), data.schedule().endTime()).toMinutes();
        if (episode.episodeLength() == duration) {
            return anime;
        }

        BoundFilterConfig filterConfig = new BoundFilterConfig(BoundLayerFilter.LAYER_KEY,
                                                               data.schedule().episode() - 1,
                                                               -1);
        SimpleIntegerLayerConfig layerConfig = new SimpleIntegerLayerConfig(EpisodeLengthLayerTransformer.LAYER_KEY,
                                                                            (int) duration);
        LayerStepConfig config = new LayerStepConfig(List.of(filterConfig), layerConfig);

        return this.animeService.updateWithData(anime.id(), AnimeUpdateData.addingLayer(config), context);
    }

    private Anime checkEpisodeCount(Anime anime, Episode episode, AnimeScheduleHolder data, RequestContext context) {
        if (String.valueOf(data.schedule().episode()).equals(episode.episodeNumber())) {
            return anime;
        }

        BoundFilterConfig filterConfig = new BoundFilterConfig(BoundLayerFilter.LAYER_KEY,
                                                               data.schedule().episode() - 1,
                                                               -1);
        SimpleOffsetIntegerLayerConfig layerConfig = new SimpleOffsetIntegerLayerConfig(EpisodeNumberLayerTransformer.LAYER_KEY,
                                                                                        data.schedule()
                                                                                            .episode() - episode.episodeId(),
                                                                                        0);
        LayerStepConfig config = new LayerStepConfig(List.of(filterConfig), layerConfig);

        return this.animeService.updateWithData(anime.id(), AnimeUpdateData.addingLayer(config), context);
    }

    private boolean isCorrectTime(Episode episode, AnimeScheduleHolder data) {
        if (episode.streamingTime() == null) {
            return false;
        }
        return Math.abs(episode.streamingTime().getEpochSecond() - data.schedule().startTime().getEpochSecond()) < 60;
    }

    private Episode getEpisode(Anime anime, AnimeScheduleHolder data, StreamType streamType, RequestContext context) {
        AnimeOptions animeOptions = AnimeOptionsBuilder.anAnimeOptions(Region.DE_DE)
                                                       .withUseRegionName(false)
                                                       .withStreamTypes(streamType)
                                                       .build();
        int index = data.schedule().episode() - 1;
        return this.episodeBuilderService.buildEpisode(anime, animeOptions, index, context);
    }
}
