package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.logic.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.episode.api.Episode;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import de.derfrzocker.anime.calendar.server.layer.api.LayerStepConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.BoundFilterConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.SimpleIntegerLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.filter.BoundLayerFilter;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.EpisodeLengthLayerTransformer;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Duration;
import java.util.List;

@ApplicationScoped
class CheckEpisodeLengthSubTask {

    @Inject
    AnimeService animeService;

    Uni<Anime> executeAsync(AnimeScheduleHolder data, Anime anime, Episode episode, RequestContext context) {
        if (isSameLength(episode, data)) {
            return Uni.createFrom().item(anime);
        }

        return Uni.createFrom().item(() -> update(data, anime, context));
    }

    private Anime update(AnimeScheduleHolder data, Anime anime, RequestContext context) {
        int episodeIndex = data.schedule().episode() - 1;
        var filter = new BoundFilterConfig(BoundLayerFilter.LAYER_KEY, episodeIndex, BoundFilterConfig.ALL_EPISODES);
        var layerConfig = new SimpleIntegerLayerConfig(EpisodeLengthLayerTransformer.LAYER_KEY,
                                                       (int) calculateDuration(data));

        var config = new LayerStepConfig(List.of(filter), layerConfig);

        return this.animeService.updateWithData(anime.id(), AnimeUpdateData.addingLayer(config), context);
    }

    private boolean isSameLength(Episode episode, AnimeScheduleHolder data) {
        return episode.episodeLength() == calculateDuration(data);
    }

    private long calculateDuration(AnimeScheduleHolder data) {
        return Duration.between(data.schedule().startTime(), data.schedule().endTime()).toMinutes();
    }
}
