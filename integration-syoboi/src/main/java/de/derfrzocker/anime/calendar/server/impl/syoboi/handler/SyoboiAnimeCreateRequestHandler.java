package de.derfrzocker.anime.calendar.server.impl.syoboi.handler;

import de.derfrzocker.anime.calendar.server.core.api.name.AnimeNameHolderService;
import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.SyoboiAnimeData;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.event.anime.PostNewAnimeFoundEvent;
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
import java.util.List;
import java.util.Optional;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.eclipse.microprofile.config.inject.ConfigProperty;

// TODO 2024-12-25: This is more or less a copy paste from NameLinkRequestHandler, refactor it.
@ApplicationScoped
public class SyoboiAnimeCreateRequestHandler {

    private static final IntegrationId SYOBOI = new IntegrationId("syoboi");
    private static final IntegrationId ANIDB = new IntegrationId("anidb");
    private static final NameType MAIN_NAME_TYPE = new NameType("main");
    private static final NameType OFFICIAL_NAME_TYPE = new NameType("official");
    private static final NameLanguage ENGLISH_LANGUAGE = new NameLanguage("en");
    private static final NameLanguage JAPAN_LANGUAGE = new NameLanguage("ja");
    private static final NameLanguage X_JAT_LANGUAGE = new NameLanguage("x-jat");

    @Inject
    AnimeNameHolderService nameHolderService;
    @ConfigProperty(name = "integration.name-link.distance-threshold")
    int distanceThreshold;
    @Inject
    Event<PostNewAnimeFoundEvent> postNewAnimeFoundEvent;
    private LevenshteinDistance levenshteinDistance;

    @PostConstruct
    void init() {
        this.levenshteinDistance = new LevenshteinDistance(this.distanceThreshold);
    }

    public Uni<Void> handleMissingLink(SyoboiAnimeData data, RequestContext context) {
        return Multi.createFrom()
                    .item(ANIDB)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .flatMap(integrationId -> collectNames(integrationId, context))
                    .map(holder -> checkHolder(holder, data.tidData().title()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(result -> isValid(result, data.tidData().title()))
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

    private Optional<NameSearchResult> checkHolder(AnimeNameHolder holder, String animeName) {
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

        return Optional.of(new NameSearchResult(holder, bestName, currentBest));
    }

    private boolean isValid(NameSearchResult nameResult, String animeName) {
        if (nameResult.score() > (animeName.length() / 2)) {
            return false;
        }

        return true;
    }

    private Multi<AnimeNameHolder> collectNames(IntegrationId integrationId, RequestContext context) {
        return Multi.createFrom().items(this.nameHolderService.getAllWithId(integrationId, context));
    }
}
