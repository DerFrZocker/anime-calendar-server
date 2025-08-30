package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.logic.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.episode.api.Episode;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import de.derfrzocker.anime.calendar.server.layer.api.LayerStepConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.BoundFilterConfig;
import de.derfrzocker.anime.calendar.server.layer.common.config.SimpleOffsetIntegerLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.filter.BoundLayerFilter;
import de.derfrzocker.anime.calendar.server.layer.common.transformer.EpisodeNumberLayerTransformer;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
class CheckEpisodeIndexSubTask {

    @Inject
    AnimeService animeService;

    Uni<Anime> executeAsync(AnimeScheduleHolder data, Anime anime, Episode episode, RequestContext context) {
        if (isCorrectIndex(episode, data)) {
            return Uni.createFrom().item(anime);
        }

        return Uni.createFrom().item(() -> update(data, anime, episode, context));
    }

    private Anime update(AnimeScheduleHolder data, Anime anime, Episode episode, RequestContext context) {
        int episodeIndex = data.schedule().episode() - 1;
        var filter = new BoundFilterConfig(BoundLayerFilter.LAYER_KEY, episodeIndex, BoundFilterConfig.ALL_EPISODES);
        var layerConfig = new SimpleOffsetIntegerLayerConfig(EpisodeNumberLayerTransformer.LAYER_KEY,
                                                             data.schedule().episode() - episode.episodeId(),
                                                             0);

        var config = new LayerStepConfig(List.of(filter), layerConfig);

        return this.animeService.updateWithData(anime.id(), AnimeUpdateData.addingLayer(config), context);
    }

    private boolean isCorrectIndex(Episode episode, AnimeScheduleHolder data) {
        return String.valueOf(data.schedule().episode()).equals(episode.episodeNumber());
    }
}
