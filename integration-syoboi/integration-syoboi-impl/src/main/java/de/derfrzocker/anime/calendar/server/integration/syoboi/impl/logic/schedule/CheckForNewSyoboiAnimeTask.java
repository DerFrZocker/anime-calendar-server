package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.logic.schedule;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.server.integration.event.PostNewAnimeFoundEvent;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameSearchResult;
import de.derfrzocker.anime.calendar.server.integration.name.service.NameSearchService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
class CheckForNewSyoboiAnimeTask {

    @Inject
    NameSearchService nameSearchService;
    @Inject
    CollectEpisodeCountSubTask collectEpisodeCountSubTask;
    @Inject
    Event<PostNewAnimeFoundEvent> postNewAnimeFoundEvent;

    Uni<Void> executeAsync(List<AnimeScheduleHolder> schedules, RequestContext context) {
        return Multi.createFrom()
                    .iterable(distinct(schedules))
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .onItem()
                    .transformToUniAndMerge(schedule -> checkHolder(schedule, context))
                    .collect()
                    .asList()
                    .replaceWithVoid();
    }

    private Uni<Void> checkHolder(AnimeScheduleHolder data, RequestContext context) {
        Uni<List<NameSearchResult>> names = searchForName(data, context);
        Uni<Integer> episodeCount = this.collectEpisodeCountSubTask.executeAsync(data, context);

        return Uni.combine()
                  .all()
                  .unis(names, episodeCount)
                  .asTuple()
                  .invoke((tuple) -> sendEvent(tuple.getItem1(), tuple.getItem2(), data, context))
                  .replaceWithVoid();
    }

    private Uni<List<NameSearchResult>> searchForName(AnimeScheduleHolder data, RequestContext context) {
        return Multi.createFrom()
                    .item(IntegrationIds.ANIDB)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .flatMap(integrationId -> this.nameSearchService.search(integrationId,
                                                                            data.tidData().title(),
                                                                            context))
                    .collect()
                    .asList();
    }

    private List<AnimeScheduleHolder> distinct(List<AnimeScheduleHolder> schedules) {
        Map<TID, AnimeScheduleHolder> values = new HashMap<>();

        for (AnimeScheduleHolder schedule : schedules) {
            values.compute(schedule.schedule().tid(), (key, value) -> {
                if (value == null) {
                    return schedule;
                }

                if (schedule.schedule().episode() < value.schedule().episode()) {
                    return schedule;
                } else {
                    return value;
                }
            });
        }

        return new ArrayList<>(values.values());
    }

    private void sendEvent(List<NameSearchResult> searchResults,
                           int episodeCount,
                           AnimeScheduleHolder data,
                           RequestContext context) {
        this.postNewAnimeFoundEvent.fire(new PostNewAnimeFoundEvent(IntegrationIds.SYOBOI,
                                                                    new IntegrationAnimeId(data.tidData().tid().raw()),
                                                                    data.tidData().title(),
                                                                    searchResults,
                                                                    episodeCount,
                                                                    context));
    }
}
