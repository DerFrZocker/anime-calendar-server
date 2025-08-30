package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.logic.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeUpdateData;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class CheckEpisodeCountSubTask {

    @Inject
    CollectEpisodeCountSubTask countSubTask;
    @Inject
    AnimeService animeService;

    Uni<Anime> executeAsync(AnimeScheduleHolder data, Anime anime, RequestContext context) {
        if (data.schedule().episode() <= anime.episodeCount()) {
            return Uni.createFrom().item(anime);
        }

        return this.countSubTask.executeAsync(data, context).map(count -> update(anime, count, context));
    }

    private Anime update(Anime anime, Integer count, RequestContext context) {
        return this.animeService.updateWithData(anime.id(), AnimeUpdateData.toEpisode(count), context);
    }
}
