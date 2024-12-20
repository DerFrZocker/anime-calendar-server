package de.derfrzocker.anime.calendar.integration.linker;

import de.derfrzocker.anime.calendar.impl.layer.config.IntegrationUrlLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.transformer.IntegrationUrlLayer;
import de.derfrzocker.anime.calendar.integration.Integrations;
import de.derfrzocker.anime.calendar.server.core.api.integration.IntegrationAnimeDao;
import de.derfrzocker.anime.calendar.server.core.api.integration.linker.IntegrationAnimeNameDao;
import de.derfrzocker.anime.calendar.server.core.api.integration.linker.IntegrationLinkService;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.model.domain.event.AnimeAddLayerEvent;
import de.derfrzocker.anime.calendar.server.model.domain.integration.linker.IntegrationNameIdData;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerHolder;
import de.derfrzocker.anime.calendar.server.model.domain.layer.LayerTransformerDataHolder;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
    @Inject
    EventBus eventBus;

    @PostConstruct
    void init() {
        levenshteinDistance = new LevenshteinDistance(distanceThreshold);
    }

    @ConsumeEvent(value = "anime-create", blocking = true)
    public void onAnimeCreate(Anime anime) {
        // TODO 2024-09-23: Move to extra place
        Set<IntegrationAnimeId> newLinks = linkAnime(Integrations.MY_ANIME_LIST_ID, anime);

        for (IntegrationAnimeId integrationAnimeId : newLinks) {
            // TODO 2024-09-24: Clean up and make better
            LayerHolder layerHolder = new LayerHolder(List.of(),
                                                      new LayerTransformerDataHolder<>(IntegrationUrlLayer.INSTANCE,
                                                                                       new IntegrationUrlLayerConfig(
                                                                                               Integrations.MY_ANIME_LIST,
                                                                                               String.format(
                                                                                                       "https://myanimelist.net/anime/%s",
                                                                                                       integrationAnimeId.raw()))));
            eventBus.publish("anime-add-layer", new AnimeAddLayerEvent(anime.id(), layerHolder));
        }
    }

    @Override
    public Set<IntegrationAnimeId> linkAnime(IntegrationId integrationId, Anime anime) {
        Set<IntegrationAnimeId> current = integrationAnimeDao.getIntegrationIds(integrationId, anime.id());

        IntegrationAnimeNameDao integrationAnimeNameDao = integrationAnimeNameDaos.select(NamedLiteral.of(integrationId.raw() + "-name-dao"))
                                                                                  .get();
        Set<IntegrationNameIdData> allAnimes = integrationAnimeNameDao.getAllAnimes();

        Pair<Set<IntegrationNameIdData>, Integer> best = getBest(allAnimes, anime);

        if (best.getRight() > distanceThreshold) {
            return Set.of();
        }

        if (best.getLeft().isEmpty()) {
            return Set.of();
        }

        Set<IntegrationNameIdData> result = new HashSet<>(best.getLeft());
        best.getLeft().stream().filter(data -> current.contains(data.integrationAnimeId())).forEach(result::remove);

        result.stream()
              .map(IntegrationNameIdData::integrationAnimeId)
              .forEach(integrationAnimeId -> integrationAnimeDao.saveOrMerge(integrationId,
                                                                             integrationAnimeId,
                                                                             anime.id()));

        return result.stream().map(IntegrationNameIdData::integrationAnimeId).collect(Collectors.toSet());
    }

    private Pair<Set<IntegrationNameIdData>, Integer> getBest(Set<IntegrationNameIdData> nameId, Anime anime) {
        Set<IntegrationNameIdData> best = new HashSet<>();
        int currentBest = Integer.MAX_VALUE;

        for (IntegrationNameIdData integrationNameIdData : nameId) {
            for (String integrationName : integrationNameIdData.names()) {

                int distance = levenshteinDistance.apply(integrationName, anime.title());
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
