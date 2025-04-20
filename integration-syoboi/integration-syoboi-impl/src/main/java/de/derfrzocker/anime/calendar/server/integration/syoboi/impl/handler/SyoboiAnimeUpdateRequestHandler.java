package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.handler;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptionsBuilder;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.episode.api.Episode;
import de.derfrzocker.anime.calendar.server.anime.api.Region;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.episode.service.EpisodeBuilderService;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import de.derfrzocker.anime.calendar.server.layer2.api.LayerStepConfig;
import de.derfrzocker.anime.calendar.server.layer2.common.config.BoundFilterConfig;
import de.derfrzocker.anime.calendar.server.layer2.common.config.SimpleIntegerLayerConfig;
import de.derfrzocker.anime.calendar.server.layer2.common.config.SimpleOffsetIntegerLayerConfig;
import de.derfrzocker.anime.calendar.server.layer2.common.config.StreamingTimeLayerConfig;
import de.derfrzocker.anime.calendar.server.layer2.common.filter.BoundLayerFilter;
import de.derfrzocker.anime.calendar.server.layer2.common.transformer.EpisodeLengthLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer2.common.transformer.EpisodeNumberLayerTransformer;
import de.derfrzocker.anime.calendar.server.layer2.common.transformer.StreamingTimeLayerTransformer;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Duration;
import java.time.Period;
import java.util.List;

@ApplicationScoped
public class SyoboiAnimeUpdateRequestHandler {

    private static final String ORG_STREAM_TYPE = "org";
    private static final String SUB_STREAM_TYPE = "sub";

    @Inject
    AnimeService animeService;
    @Inject
    EpisodeBuilderService episodeBuilderService;

    public Uni<Void> handlePresentLink(List<AnimeIntegrationLink> links,
                                       AnimeScheduleHolder data,
                                       RequestContext context) {
        return Multi.createFrom()
                    .iterable(links)
                    .onItem()
                    .invoke(link -> handleSinglePresentLink(link, data, context))
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

        Episode episode = getEpisode(anime, data, ORG_STREAM_TYPE, context);

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
        Episode subEpisode = getEpisode(anime, data, SUB_STREAM_TYPE, context);
        if (subEpisode.streamingTime() != null && episode.streamingTime() != null) {
            Duration diff = Duration.between(episode.streamingTime(), subEpisode.streamingTime());

            BoundFilterConfig filterConfig = new BoundFilterConfig(BoundLayerFilter.LAYER_KEY,
                                                                   data.schedule().episode() - 1,
                                                                   -1);
            StreamingTimeLayerConfig layerConfig = new StreamingTimeLayerConfig(StreamingTimeLayerTransformer.LAYER_KEY,
                                                                                data.schedule().startTime().plus(diff),
                                                                                Period.ofDays(7),
                                                                                data.schedule().episode() - 1,
                                                                                SUB_STREAM_TYPE);
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
                                                                            ORG_STREAM_TYPE);
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

    private Episode getEpisode(Anime anime, AnimeScheduleHolder data, String streamType, RequestContext context) {
        AnimeOptions animeOptions = AnimeOptionsBuilder.anAnimeOptions(Region.DE_DE)
                                                       .withUseRegionName(false)
                                                       .withStreamType(streamType)
                                                       .build();
        int index = data.schedule().episode() - 1;
        return this.episodeBuilderService.buildEpisode(anime, animeOptions, index, context);
    }
}
