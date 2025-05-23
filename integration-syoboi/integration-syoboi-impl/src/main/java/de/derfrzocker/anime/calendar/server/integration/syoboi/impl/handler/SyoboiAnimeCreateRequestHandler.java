package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.handler;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.server.integration.name.service.NameSearchService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.holder.AnimeScheduleHolder;
import de.derfrzocker.anime.calendar.server.integration.event.PostNewAnimeFoundEvent;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameSearchResult;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import java.util.List;

@Dependent
public class SyoboiAnimeCreateRequestHandler {

    @Inject
    NameSearchService nameSearchService;
    @Inject
    Event<PostNewAnimeFoundEvent> postNewAnimeFoundEvent;

    public Uni<Void> handleMissingLink(AnimeScheduleHolder data, RequestContext context) {
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

    private void sendEvent(List<NameSearchResult> searchResults, AnimeScheduleHolder data, RequestContext context) {
        this.postNewAnimeFoundEvent.fire(new PostNewAnimeFoundEvent(IntegrationIds.SYOBOI,
                                                                    new IntegrationAnimeId(data.tidData().tid().raw()),
                                                                    data.tidData().title(),
                                                                    searchResults,
                                                                    context));
    }
}
