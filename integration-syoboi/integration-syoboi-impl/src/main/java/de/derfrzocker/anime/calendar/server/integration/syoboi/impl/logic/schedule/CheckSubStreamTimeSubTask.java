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
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import de.derfrzocker.anime.calendar.server.layer.api.LayerStepConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.BoundFilterConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.StreamingTimeLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.filter.BoundLayerFilter;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.StreamingTimeLayerTransformer;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Duration;
import java.time.Period;
import java.util.List;

@ApplicationScoped
class CheckSubStreamTimeSubTask {

    private static final Duration TOLERANCE = Duration.ofMinutes(30);

    @Inject
    AnimeService animeService;
    @Inject
    EpisodeBuilderService episodeBuilderService;

    Uni<Anime> executeAsync(AnimeScheduleHolder data, Anime anime, Episode orgEpisode, RequestContext context) {
        if (isSameTimeOrNotPresent(orgEpisode, data)) {
            return Uni.createFrom().item(anime);
        }

        Episode subEpisode = getEpisode(anime, data, context);
        if (subEpisode.streamingTime() == null) {
            return Uni.createFrom().item(anime);
        }

        return Uni.createFrom().item(() -> update(data, anime, orgEpisode, subEpisode, context));
    }

    private Anime update(AnimeScheduleHolder data,
                         Anime anime,
                         Episode orgEpisode,
                         Episode subEpisode,
                         RequestContext context) {
        Duration diff = Duration.between(orgEpisode.streamingTime(), subEpisode.streamingTime());
        int episodeIndex = data.schedule().episode() - 1;
        var filter = new BoundFilterConfig(BoundLayerFilter.LAYER_KEY, episodeIndex, BoundFilterConfig.ALL_EPISODES);
        var layerConfig = new StreamingTimeLayerConfig(StreamingTimeLayerTransformer.LAYER_KEY,
                                                       data.schedule().startTime().plus(diff),
                                                       Period.ofDays(7),
                                                       episodeIndex,
                                                       StreamType.SUB);
        var config = new LayerStepConfig(List.of(filter), layerConfig);

        return this.animeService.updateWithData(anime.id(), AnimeUpdateData.addingLayer(config), context);
    }

    private boolean isSameTimeOrNotPresent(Episode episode, AnimeScheduleHolder data) {
        if (episode.streamingTime() == null) {
            return true;
        }

        long oldStreamingSeconds = episode.streamingTime().getEpochSecond();
        long newStreamingSeconds = data.schedule().startTime().getEpochSecond();
        long diffSeconds = Math.abs(oldStreamingSeconds - newStreamingSeconds);

        return diffSeconds < TOLERANCE.getSeconds();
    }

    private Episode getEpisode(Anime anime, AnimeScheduleHolder data, RequestContext context) {
        AnimeOptions animeOptions = AnimeOptionsBuilder.anAnimeOptions(Region.DE_DE)
                                                       .withUseRegionName(false)
                                                       .withStreamTypes(StreamType.SUB)
                                                       .build();
        int index = data.schedule().episode() - 1;
        return this.episodeBuilderService.buildEpisode(anime, animeOptions, index, context);
    }
}
