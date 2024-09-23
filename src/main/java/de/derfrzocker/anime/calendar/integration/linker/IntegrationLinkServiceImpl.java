package de.derfrzocker.anime.calendar.integration.linker;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeDao;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationId;
import de.derfrzocker.anime.calendar.api.integration.linker.IntegrationAnimeNameDao;
import de.derfrzocker.anime.calendar.api.integration.linker.IntegrationLinkService;
import de.derfrzocker.anime.calendar.api.integration.linker.IntegrationNameIdData;
import de.derfrzocker.anime.calendar.integration.Integrations;
import io.quarkus.vertx.ConsumeEvent;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class IntegrationLinkServiceImpl implements IntegrationLinkService {

    @Inject
    IntegrationAnimeDao integrationAnimeDao;
    @Inject
    Instance<IntegrationAnimeNameDao> integrationAnimeNameDaos;
    @ConfigProperty(name = "integration.distance-threshold")
    int distanceThreshold;
    LevenshteinDistance levenshteinDistance;

    @PostConstruct
    void init() {
        levenshteinDistance = new LevenshteinDistance(distanceThreshold);
    }

    @ConsumeEvent(value = "anime-create")
    public void onAnimeCreate(Anime anime) {
        // TODO 2024-09-23: Move to extra place
        linkAnime(Integrations.MY_ANIME_LIST_ID, anime);
    }

    @Override
    public void linkAnime(IntegrationId integrationId, Anime anime) {
        Set<IntegrationAnimeId> current = integrationAnimeDao.getIntegrationIds(integrationId, anime.animeId());

        IntegrationAnimeNameDao integrationAnimeNameDao = integrationAnimeNameDaos.select(NamedLiteral.of(integrationId.id() + "-name-dao")).get();
        Set<IntegrationNameIdData> allAnimes = integrationAnimeNameDao.getAllAnimes();

        Pair<Set<IntegrationNameIdData>, Integer> best = getBest(allAnimes, anime);

        if (best.getRight() > distanceThreshold) {
            return;
        }

        if (best.getLeft().isEmpty()) {
            return;
        }

        Set<IntegrationNameIdData> result = new HashSet<>(best.getLeft());
        best.getLeft().stream().filter(data -> current.contains(data.integrationAnimeId())).forEach(result::remove);

        result.stream().map(IntegrationNameIdData::integrationAnimeId).forEach(integrationAnimeId -> integrationAnimeDao.saveOrMerge(integrationId, integrationAnimeId, anime.animeId()));
    }

    private Pair<Set<IntegrationNameIdData>, Integer> getBest(Set<IntegrationNameIdData> nameId, Anime anime) {
        Set<IntegrationNameIdData> best = new HashSet<>();
        int currentBest = Integer.MAX_VALUE;

        for (IntegrationNameIdData integrationNameIdData : nameId) {
            for (String integrationName : integrationNameIdData.names()) {

                int distance = levenshteinDistance.apply(integrationName, anime.animeName());
                if (distance == -1) {
                    continue;
                }
                if (distance < currentBest) {
                    currentBest = distance;
                    best.clear();
                    best.add(integrationNameIdData);
                } else if (distance == currentBest) {
                    best.add(integrationNameIdData);
                }
            }
        }

        return Pair.of(best, currentBest);
    }
}
