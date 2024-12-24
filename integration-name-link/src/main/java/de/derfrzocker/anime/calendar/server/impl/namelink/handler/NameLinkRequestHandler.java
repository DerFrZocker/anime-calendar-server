package de.derfrzocker.anime.calendar.server.impl.namelink.handler;

import de.derfrzocker.anime.calendar.server.core.api.name.AnimeNameHolderService;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.event.name.PostNameLinkSearchEvent;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeName;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameLanguage;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameSearchResult;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameType;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class NameLinkRequestHandler {

    private static final IntegrationId ANIDB = new IntegrationId("anidb");
    private static final IntegrationId MY_ANIME_LIST = new IntegrationId("myanimelist");
    private static final NameType MAIN_NAME_TYPE = new NameType("main");
    private static final NameType OFFICIAL_NAME_TYPE = new NameType("official");
    private static final NameLanguage ENGLISH_LANGUAGE = new NameLanguage("en");
    private static final NameLanguage JAPAN_LANGUAGE = new NameLanguage("ja");
    private static final NameLanguage X_JAT_LANGUAGE = new NameLanguage("x-jat");

    private static final Logger LOG = Logger.getLogger(NameLinkRequestHandler.class);

    @Inject
    AnimeNameHolderService animeNameHolderService;
    @Inject
    Event<PostNameLinkSearchEvent> nameLinkSearchEvent;
    @ConfigProperty(name = "integration.name-link.distance-threshold")
    int distanceThreshold;
    private LevenshteinDistance levenshteinDistance;

    @PostConstruct
    void init() {
        this.levenshteinDistance = new LevenshteinDistance(this.distanceThreshold);
    }

    public Uni<Void> checkForLinks(Anime anime, RequestContext context) {
        LOG.info("Searching anime integration for anime '%s'.".formatted(anime.id().raw()));

        return Multi.createFrom()
                    .items(ANIDB, MY_ANIME_LIST)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .flatMap(integrationId -> collectNames(integrationId, context))
                    .map(holder -> checkHolder(holder, anime.title()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(result -> isValid(result, anime.title()))
                    .collect()
                    .asMultiMap(result -> result.nameHolder().integrationId())
                    .invoke(results -> {
                        for (Map.Entry<IntegrationId, Collection<NameResult>> entry : results.entrySet()) {
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

    private void sendEvent(Anime anime, Map<IntegrationId, Collection<NameResult>> results, RequestContext context) {
        // TODO 2024-12-23: This method needs to be made better
        Map<IntegrationId, List<NameSearchResult>> searchResults = new HashMap<>();
        for (Map.Entry<IntegrationId, Collection<NameResult>> entry : results.entrySet()) {
            List<NameSearchResult> nameSearchResults = new ArrayList<>();
            for (NameResult nameResult : entry.getValue()) {
                nameSearchResults.add(new NameSearchResult(nameResult.nameHolder(),
                                                           nameResult.bestName(),
                                                           nameResult.score()));
            }

            searchResults.put(entry.getKey(), nameSearchResults);
        }

        this.nameLinkSearchEvent.fire(new PostNameLinkSearchEvent(anime, searchResults, context));
    }

    private Optional<NameResult> checkHolder(AnimeNameHolder holder, String animeName) {
        int currentBest = Integer.MAX_VALUE;
        AnimeName bestName = null;
        for (AnimeName animeTitle : holder.names()) {
            if (!animeTitle.type().equals(MAIN_NAME_TYPE) && !animeTitle.type().equals(OFFICIAL_NAME_TYPE)) {
                continue;
            }
            if (!animeTitle.language().equals(ENGLISH_LANGUAGE) && !animeTitle.language()
                                                                              .equals(JAPAN_LANGUAGE) && !animeTitle.language()
                                                                                                                    .equals(X_JAT_LANGUAGE)) {
                continue;
            }

            int distance = this.levenshteinDistance.apply(animeTitle.name(), animeName);
            if (distance == -1) {
                continue;
            }

            if (distance < currentBest) {
                currentBest = distance;
                bestName = animeTitle;
            }
        }

        if (currentBest == Integer.MAX_VALUE || bestName == null) {
            return Optional.empty();
        }

        return Optional.of(new NameResult(holder, bestName, currentBest));
    }

    private boolean isValid(NameResult nameResult, String animeName) {
        if (nameResult.score() > (animeName.length() / 2)) {
            return false;
        }

        return true;
    }

    private Multi<AnimeNameHolder> collectNames(IntegrationId integrationId, RequestContext context) {
        return Multi.createFrom().items(this.animeNameHolderService.getAllWithId(integrationId, context));
    }

    private record NameResult(AnimeNameHolder nameHolder, AnimeName bestName, int score) {

    }
}
