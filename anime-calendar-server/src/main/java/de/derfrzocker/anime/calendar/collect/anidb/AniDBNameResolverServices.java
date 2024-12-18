package de.derfrzocker.anime.calendar.collect.anidb;

import de.derfrzocker.anime.calendar.server.core.api.name.AnimeNameHolderService;
import de.derfrzocker.anime.calendar.server.core.api.season.AnimeSeasonInfoService;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeName;
import de.derfrzocker.anime.calendar.server.model.domain.name.AnimeNameHolder;
import de.derfrzocker.anime.calendar.server.model.domain.name.NameType;
import de.derfrzocker.anime.calendar.server.model.domain.season.AnimeSeasonInfo;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AniDBNameResolverServices {

    @ConfigProperty(name = "anidb-name-resolver.distance-threshold")
    int distanceThreshold;
    LevenshteinDistance levenshteinDistance;
    @ConfigProperty(name = "anidb-name-resolver.language")
    String language;
    @Inject
    AnimeSeasonInfoService seasonService;
    @Inject
    AnimeNameHolderService nameService;

    @PostConstruct
    void init() {
        levenshteinDistance = new LevenshteinDistance(distanceThreshold);
    }

    public AnimeNameSearchResult convertName(String name) {
        // TODO 2024-12-18: Null context
        List<AnimeNameHolder> allAnimeNames = nameService.getAll(null).toList();
        // TODO 2024-12-18: Null context
        List<AnimeNameHolder> animeNames = filterAnimeNames(allAnimeNames, seasonService.getAll(null).toList());

        Pair<Set<AnimeNameHolder>, Integer> best = getBest(animeNames, name);

        if (!isValid(best)) {
            best = getBest(allAnimeNames, name);
        }

        if (!isValid(best)) {
            return null;
        }

        if (best.getLeft().size() != 1) {
            // throw new RuntimeException("Cannot determinate anime for name " + name + " got following results " + best.getLeft());
            // System.out.println("Ambiges values " + best.getRight() + " " + name + " " + best.getLeft().size() + " " + best.getLeft());
            return null;
        }

        AnimeNameHolder result = best.getLeft().iterator().next();

        AnimeName animeTitle = findBestTitle(result);

        if (animeTitle == null) {
            return null;
        }

        return new AnimeNameSearchResult(result.integrationAnimeId(), animeTitle.name(), best.getRight());
    }

    private boolean isValid(Pair<Set<AnimeNameHolder>, Integer> best) {
        if (best.getRight() > distanceThreshold) {
            return false;
        }

        if (best.getLeft().isEmpty()) {
            return false;
        }

        return true;
    }

    private AnimeName findBestTitle(AnimeNameHolder animeNames) {
        for (AnimeName animeTitle : animeNames.names()) {
            if (animeTitle.type().equals(new NameType("main")) && animeTitle.language().equals(language)) {
                return animeTitle;
            }
        }

//        throw new RuntimeException("No main language set for " + animeNames);
        return null;
    }

    private List<AnimeNameHolder> filterAnimeNames(List<AnimeNameHolder> animeNames, List<AnimeSeasonInfo> seasonData) {
        List<AnimeNameHolder> filteredAnimeNames = new ArrayList<>();

        seasonData.forEach(season -> animeNames.stream()
                                               .filter(names -> names.integrationAnimeId()
                                                                     .equals(season.integrationAnimeId()))
                                               .forEach(filteredAnimeNames::add));

        return filteredAnimeNames;
    }

    private Pair<Set<AnimeNameHolder>, Integer> getBest(List<AnimeNameHolder> names, String name) {
        Set<AnimeNameHolder> best = new HashSet<>();
        int currentBest = Integer.MAX_VALUE;

        for (AnimeNameHolder animeName : names) {
            for (AnimeName animeTitle : animeName.names()) {
                // TODO 2024-09-30: Magic value
                if (!animeTitle.language().equals("ja") && !animeTitle.language().equals("en") && !animeTitle.name()
                                                                                                             .equals(language)) {
                    continue;
                }

                if (!animeTitle.type().equals(new NameType("main")) && !animeTitle.type()
                                                                                  .equals(new NameType("official"))) {
                    continue;
                }

                int distance = levenshteinDistance.apply(animeTitle.name(), name);
                if (distance == -1) {
                    continue;
                }
                if (distance < currentBest) {
                    currentBest = distance;
                    best.clear();
                    best.add(animeName);
                } else if (distance == currentBest) {
                    best.add(animeName);
                }
            }
        }

        if (currentBest > (name.length() / 2)) {
            return Pair.of(Set.of(), Integer.MAX_VALUE);
        }

        return Pair.of(best, currentBest);
    }
}
