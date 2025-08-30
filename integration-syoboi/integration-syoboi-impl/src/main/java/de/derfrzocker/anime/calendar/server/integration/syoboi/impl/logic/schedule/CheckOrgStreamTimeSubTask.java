package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.logic.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.episode.api.Episode;
import de.derfrzocker.anime.calendar.server.episode.api.StreamType;
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
class CheckOrgStreamTimeSubTask {

    private static final Duration TOLERANCE = Duration.ofMinutes(1);

    @Inject
    AnimeService animeService;

    Uni<Anime> executeAsync(AnimeScheduleHolder data, Anime anime, Episode episode, RequestContext context) {
        if (isSameTime(episode, data)) {
            return Uni.createFrom().item(anime);
        }

        return Uni.createFrom().item(() -> update(data, anime, context));
    }

    private Anime update(AnimeScheduleHolder data, Anime anime, RequestContext context) {
        int episodeIndex = data.schedule().episode() - 1;
        var filter = new BoundFilterConfig(BoundLayerFilter.LAYER_KEY, episodeIndex, BoundFilterConfig.ALL_EPISODES);
        var layerConfig = new StreamingTimeLayerConfig(StreamingTimeLayerTransformer.LAYER_KEY,
                                                       data.schedule().startTime(),
                                                       Period.ofDays(7),
                                                       episodeIndex,
                                                       StreamType.ORG);
        var config = new LayerStepConfig(List.of(filter), layerConfig);

        return this.animeService.updateWithData(anime.id(), AnimeUpdateData.addingLayer(config), context);
    }

    private boolean isSameTime(Episode episode, AnimeScheduleHolder data) {
        if (episode.streamingTime() == null) {
            return false;
        }

        long oldStreamingSeconds = episode.streamingTime().getEpochSecond();
        long newStreamingSeconds = data.schedule().startTime().getEpochSecond();
        long diffSeconds = Math.abs(oldStreamingSeconds - newStreamingSeconds);

        return diffSeconds < TOLERANCE.getSeconds();
    }
}
