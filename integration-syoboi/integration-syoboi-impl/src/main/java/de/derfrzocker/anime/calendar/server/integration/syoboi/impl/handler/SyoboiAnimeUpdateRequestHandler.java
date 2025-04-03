package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.handler;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.anime.api.Episode;
import de.derfrzocker.anime.calendar.server.anime.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.server.anime.api.Region;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerFilterDataHolder;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerHolder;
import de.derfrzocker.anime.calendar.server.anime.api.layer.LayerTransformerDataHolder;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import de.derfrzocker.anime.calendar.server.layer.config.BoundFilterConfig;
import de.derfrzocker.anime.calendar.server.layer.config.SimpleIntegerLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.config.StreamingTimeLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.filter.BoundLayerFilter;
import de.derfrzocker.anime.calendar.server.layer.transformer.EpisodeLengthLayer;
import de.derfrzocker.anime.calendar.server.layer.transformer.StreamingTimeLayer;
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

        Episode episode = getEpisode(anime, data, ORG_STREAM_TYPE);

        anime = checkBaseStreamTime(anime, episode, data, context);
        anime = checkLength(anime, episode, data, context);
    }

    private Anime checkBaseStreamTime(Anime anime, Episode episode, AnimeScheduleHolder data, RequestContext context) {
        if (isCorrectTime(episode, data)) {
            return anime;
        }

        // TODO 2024-12-30: Handle multiple streaming services, broadcasting the same anime.
        // TODO 2024-12-30: Make this way better
        Episode subEpisode = getEpisode(anime, data, SUB_STREAM_TYPE);
        if (subEpisode.streamingTime() != null && episode.streamingTime() != null) {
            Duration diff = Duration.between(episode.streamingTime(), subEpisode.streamingTime());

            LayerFilterDataHolder<BoundFilterConfig> layerFilterDataHolder = new LayerFilterDataHolder<>(
                    BoundLayerFilter.INSTANCE,
                    new BoundFilterConfig(data.schedule().episode() - 1, -1));
            LayerTransformerDataHolder<StreamingTimeLayerConfig> layerTransformerDataHolder = new LayerTransformerDataHolder<>(
                    StreamingTimeLayer.INSTANCE,
                    new StreamingTimeLayerConfig(data.schedule().startTime().plus(diff),
                                                 Period.ofDays(7),
                                                 data.schedule().episode() - 1,
                                                 SUB_STREAM_TYPE));
            LayerHolder layerHolder = new LayerHolder(List.of(layerFilterDataHolder), layerTransformerDataHolder);

            anime = this.animeService.updateWithData(anime.id(), AnimeUpdateData.addingLayer(layerHolder), context);
        }

        LayerFilterDataHolder<BoundFilterConfig> layerFilterDataHolder = new LayerFilterDataHolder<>(BoundLayerFilter.INSTANCE,
                                                                                                     new BoundFilterConfig(
                                                                                                             data.schedule()
                                                                                                                 .episode() - 1,
                                                                                                             -1));
        LayerTransformerDataHolder<StreamingTimeLayerConfig> layerTransformerDataHolder = new LayerTransformerDataHolder<>(
                StreamingTimeLayer.INSTANCE,
                new StreamingTimeLayerConfig(data.schedule().startTime(),
                                             Period.ofDays(7),
                                             data.schedule().episode() - 1,
                                             ORG_STREAM_TYPE));
        LayerHolder layerHolder = new LayerHolder(List.of(layerFilterDataHolder), layerTransformerDataHolder);

        return this.animeService.updateWithData(anime.id(), AnimeUpdateData.addingLayer(layerHolder), context);
    }

    private Anime checkLength(Anime anime, Episode episode, AnimeScheduleHolder data, RequestContext context) {
        long duration = Duration.between(data.schedule().startTime(), data.schedule().endTime()).toMinutes();
        if (episode.episodeLength() == duration) {
            return anime;
        }

        LayerFilterDataHolder<BoundFilterConfig> layerFilterDataHolder = new LayerFilterDataHolder<>(BoundLayerFilter.INSTANCE,
                                                                                                     new BoundFilterConfig(
                                                                                                             data.schedule()
                                                                                                                 .episode() - 1,
                                                                                                             -1));
        LayerTransformerDataHolder<SimpleIntegerLayerConfig> layerTransformerDataHolder = new LayerTransformerDataHolder<>(
                EpisodeLengthLayer.INSTANCE,
                new SimpleIntegerLayerConfig((int) duration));
        LayerHolder layerHolder = new LayerHolder(List.of(layerFilterDataHolder), layerTransformerDataHolder);

        return this.animeService.updateWithData(anime.id(), AnimeUpdateData.addingLayer(layerHolder), context);
    }

    private boolean isCorrectTime(Episode episode, AnimeScheduleHolder data) {
        if (episode.streamingTime() == null) {
            return false;
        }
        return Math.abs(episode.streamingTime().getEpochSecond() - data.schedule().startTime().getEpochSecond()) < 60;
    }

    private Episode getEpisode(Anime anime, AnimeScheduleHolder data, String streamType) {
        EpisodeBuilder episodeBuilder = EpisodeBuilder.anEpisode(data.schedule().episode() - 1);
        AnimeOptions animeOptions = new AnimeOptions(Region.DE_DE, false, streamType);
        for (LayerHolder layerHolder : anime.episodeLayers().reversed()) {
            if (layerHolder.shouldSkip(anime, animeOptions, episodeBuilder)) {
                continue;
            }
            layerHolder.layerDataHolder().transform(anime, animeOptions, episodeBuilder);
        }

        return episodeBuilder.build();
    }
}
