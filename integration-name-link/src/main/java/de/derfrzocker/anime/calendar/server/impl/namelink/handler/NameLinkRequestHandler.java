package de.derfrzocker.anime.calendar.server.impl.namelink.handler;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.core.api.name.NameSearchService;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.event.name.PostNameLinkSearchEvent;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameSearchResult;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.Map;
import org.jboss.logging.Logger;

@Dependent
public class NameLinkRequestHandler {

    private static final IntegrationId ANIDB = new IntegrationId("anidb");
    private static final IntegrationId MY_ANIME_LIST = new IntegrationId("myanimelist");

    private static final Logger LOG = Logger.getLogger(NameLinkRequestHandler.class);

    @Inject
    NameSearchService nameSearchService;
    @Inject
    Event<PostNameLinkSearchEvent> nameLinkSearchEvent;

    public Uni<Void> checkForLinks(Anime anime, RequestContext context) {
        LOG.info("Searching anime integration for anime '%s'.".formatted(anime.id().raw()));

        return Multi.createFrom()
                    .items(ANIDB, MY_ANIME_LIST)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .flatMap(integrationId -> this.nameSearchService.search(integrationId, anime.title(), context))
                    .collect()
                    .asMultiMap(result -> result.animeNameHolder().integrationId())
                    .invoke(results -> {
                        for (Map.Entry<IntegrationId, Collection<NameSearchResult>> entry : results.entrySet()) {
                            LOG.info("Found '%d' anime names for integration '%s' and anime '%s'.".formatted(entry.getValue()
                                                                                                                  .size(),
                                                                                                             entry.getKey()
                                                                                                                  .raw(),
                                                                                                             anime.id()
                                                                                                                  .raw()));
                        }
                    })
                    .invoke(results -> sendEvent(anime, results, context))
                    .onFailure()
                    .invoke(error -> {
                        LOG.error("Anime integration linking failed for anime '%s'.".formatted(anime.id().raw()),
                                  error);
                    })
                    .invoke(results -> LOG.info("Anime integration linking successfully send for anime '%s'.".formatted(
                            anime.id().raw())))
                    .replaceWithVoid();
    }

    private void sendEvent(Anime anime,
                           Map<IntegrationId, Collection<NameSearchResult>> results,
                           RequestContext context) {
        this.nameLinkSearchEvent.fire(new PostNameLinkSearchEvent(anime, results, context));
    }
}
