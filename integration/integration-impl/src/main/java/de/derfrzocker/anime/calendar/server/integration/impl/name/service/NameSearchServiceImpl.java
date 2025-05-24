package de.derfrzocker.anime.calendar.server.integration.impl.name.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeName;
import de.derfrzocker.anime.calendar.server.integration.name.api.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameLanguage;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameSearchResult;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameType;
import de.derfrzocker.anime.calendar.server.integration.name.service.AnimeNameHolderService;
import de.derfrzocker.anime.calendar.server.integration.name.service.NameSearchService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class NameSearchServiceImpl implements NameSearchService {

    private static final NameType MAIN_NAME_TYPE = new NameType("main");
    private static final NameType OFFICIAL_NAME_TYPE = new NameType("official");
    private static final NameLanguage ENGLISH_LANGUAGE = new NameLanguage("en");
    private static final NameLanguage JAPAN_LANGUAGE = new NameLanguage("ja");
    private static final NameLanguage X_JAT_LANGUAGE = new NameLanguage("x-jat");

    @Inject
    AnimeNameHolderService nameHolderService;
    @ConfigProperty(name = "name-search-service.distance-threshold")
    int distanceThreshold;
    private LevenshteinDistance levenshteinDistance;

    @PostConstruct
    void init() {
        this.levenshteinDistance = new LevenshteinDistance(this.distanceThreshold);
    }

    @Override
    public Multi<NameSearchResult> search(IntegrationId id, String name, RequestContext context) {
        return Multi.createFrom()
                    .item(id)
                    .emitOn(Infrastructure.getDefaultExecutor())
                    .flatMap(integrationId -> collectNames(integrationId, context))
                    .map(holder -> checkHolder(holder, name))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(result -> isValid(result, name));
    }

    private Optional<NameSearchResult> checkHolder(AnimeNameHolder holder, String animeName) {
        int currentBest = Integer.MAX_VALUE;
        AnimeName bestName = null;
        for (AnimeName animeTitle : holder.names()) {
            if (!isValidType(animeTitle.type())) {
                continue;
            }
            if (!isValidLanguage(animeTitle.language())) {
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

    private boolean isValidType(NameType type) {
        if (MAIN_NAME_TYPE.equals(type)) {
            return true;
        }

        if (OFFICIAL_NAME_TYPE.equals(type)) {
            return true;
        }

        return false;
    }

    private boolean isValidLanguage(NameLanguage language) {
        if (ENGLISH_LANGUAGE.equals(language)) {
            return true;
        }

        if (JAPAN_LANGUAGE.equals(language)) {
            return true;
        }

        if (X_JAT_LANGUAGE.equals(language)) {
            return true;
        }

        return false;
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
