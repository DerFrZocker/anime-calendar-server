package de.derfrzocker.anime.calendar.server.impl.namelink.handler;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.integration.name.service.NameSearchService;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.integration.name.event.PostNameLinkSearchEvent;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameSearchResult;
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

    private static final Logger LOG = Logger.getLogger(NameLinkRequestHandler.class);

    @Inject
    NameSearchService nameSearchService;
    @Inject
    AnimeIntegrationLinkService linkService;
    @Inject
    Event<PostNameLinkSearchEvent> nameLinkSearchEvent;

    public Uni<Void> checkForLinks(Anime anime, RequestContext context) {
        LOG.info("Searching anime integration for anime '%s'.".formatted(anime.id().raw()));

        return Multi.createFrom()
                    .items(IntegrationIds.ANIDB, IntegrationIds.MY_ANIME_LIST)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .filter(integrationId -> isNotLinked(anime, integrationId, context))
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

    private boolean isNotLinked(Anime anime, IntegrationId integrationId, RequestContext context) {
        return this.linkService.getAllWithId(anime.id(), integrationId, context).findAny().isEmpty();
    }
}
