package de.derfrzocker.anime.calendar.server.impl.syoboi.handler;

import de.derfrzocker.anime.calendar.server.core.api.name.NameSearchService;
import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.SyoboiAnimeData;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.event.anime.PostNewAnimeFoundEvent;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameSearchResult;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import java.util.List;

@Dependent
public class SyoboiAnimeCreateRequestHandler {

    private static final IntegrationId SYOBOI = new IntegrationId("syoboi");
    private static final IntegrationId ANIDB = new IntegrationId("anidb");

    @Inject
    NameSearchService nameSearchService;
    @Inject
    Event<PostNewAnimeFoundEvent> postNewAnimeFoundEvent;

    public Uni<Void> handleMissingLink(SyoboiAnimeData data, RequestContext context) {
        return Multi.createFrom()
                    .item(ANIDB)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .flatMap(integrationId -> this.nameSearchService.search(integrationId,
                                                                            data.tidData().title(),
                                                                            context))
                    .collect()
                    .asList()
                    .invoke(result -> sendEvent(result, data, context))
                    .replaceWithVoid();
    }

    private void sendEvent(List<NameSearchResult> searchResults, SyoboiAnimeData data, RequestContext context) {
        this.postNewAnimeFoundEvent.fire(new PostNewAnimeFoundEvent(SYOBOI,
                                                                    new IntegrationAnimeId(data.tidData().tid().raw()),
                                                                    data.tidData().title(),
                                                                    searchResults,
                                                                    context));
    }
}
