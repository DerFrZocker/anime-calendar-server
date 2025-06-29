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
    Event<PostNewAnimeFoundEvent> postNewAnimeFoundEvent;

    Uni<Void> executeAsync(List<AnimeScheduleHolder> schedules, RequestContext context) {
        return Multi.createFrom()
                    .iterable(distinct(schedules))
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .onItem()
                    .transformToUniAndMerge(schedule -> searchForName(schedule, context))
                    .collect()
                    .asList()
                    .replaceWithVoid();
    }

    private Uni<Void> searchForName(AnimeScheduleHolder data, RequestContext context) {
        return Multi.createFrom()
                    .item(IntegrationIds.ANIDB)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .flatMap(integrationId -> this.nameSearchService.search(integrationId,
                                                                            data.tidData().title(),
                                                                            context))
                    .collect()
                    .asList()
                    .invoke(result -> sendEvent(result, data, context))
                    .replaceWithVoid();
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

    private void sendEvent(List<NameSearchResult> searchResults, AnimeScheduleHolder data, RequestContext context) {
        this.postNewAnimeFoundEvent.fire(new PostNewAnimeFoundEvent(IntegrationIds.SYOBOI,
                                                                    new IntegrationAnimeId(data.tidData().tid().raw()),
                                                                    data.tidData().title(),
                                                                    searchResults,
                                                                    context));
    }
}
