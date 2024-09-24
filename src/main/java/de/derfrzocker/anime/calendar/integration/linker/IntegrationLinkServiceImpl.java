package de.derfrzocker.anime.calendar.integration.linker;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.event.AnimeAddLayerEvent;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeDao;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationId;
import de.derfrzocker.anime.calendar.api.integration.linker.IntegrationAnimeNameDao;
import de.derfrzocker.anime.calendar.api.integration.linker.IntegrationLinkService;
import de.derfrzocker.anime.calendar.api.integration.linker.IntegrationNameIdData;
import de.derfrzocker.anime.calendar.api.layer.LayerHolder;
import de.derfrzocker.anime.calendar.api.layer.LayerTransformerDataHolder;
import de.derfrzocker.anime.calendar.impl.layer.config.IntegrationUrlLayerConfig;
import de.derfrzocker.anime.calendar.impl.layer.transformer.IntegrationUrlLayer;
import de.derfrzocker.anime.calendar.integration.Integrations;
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

    @ConsumeEvent(value = "anime-create")
    public void onAnimeCreate(Anime anime) {
        // TODO 2024-09-23: Move to extra place
        Set<IntegrationAnimeId> newLinks = linkAnime(Integrations.MY_ANIME_LIST_ID, anime);

        for (IntegrationAnimeId integrationAnimeId : newLinks) {
            // TODO 2024-09-24: Clean up and make better
            LayerHolder layerHolder = new LayerHolder(List.of(), new LayerTransformerDataHolder<>(IntegrationUrlLayer.INSTANCE, new IntegrationUrlLayerConfig(Integrations.MY_ANIME_LIST, String.format("https://myanimelist.net/anime/%s", integrationAnimeId.id()))));
            eventBus.publish("anime-add-layer", new AnimeAddLayerEvent(anime.animeId(), layerHolder));
        }
    }

    @Override
    public Set<IntegrationAnimeId> linkAnime(IntegrationId integrationId, Anime anime) {
        Set<IntegrationAnimeId> current = integrationAnimeDao.getIntegrationIds(integrationId, anime.animeId());

        IntegrationAnimeNameDao integrationAnimeNameDao = integrationAnimeNameDaos.select(NamedLiteral.of(integrationId.id() + "-name-dao")).get();
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

        result.stream().map(IntegrationNameIdData::integrationAnimeId).forEach(integrationAnimeId -> integrationAnimeDao.saveOrMerge(integrationId, integrationAnimeId, anime.animeId()));

        return result.stream().map(IntegrationNameIdData::integrationAnimeId).collect(Collectors.toSet());
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
