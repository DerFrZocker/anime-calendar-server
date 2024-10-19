package de.derfrzocker.anime.calendar.integration.myanimelist;

import de.derfrzocker.anime.calendar.server.core.api.integration.linker.IntegrationAnimeNameDao;
import de.derfrzocker.anime.calendar.integration.Integrations;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.integration.linker.IntegrationNameIdData;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Named(Integrations.MY_ANIME_LIST + "-name-dao")
public class IntegrationAnimeNameDaoImpl implements IntegrationAnimeNameDao {

    @RestClient
    IntegrationAnimeRestClient integrationAnimeRestClient;

    @ConfigProperty(name = "my-anime-list-integration.current-season")
    Season currentSeason;
    @ConfigProperty(name = "my-anime-list-integration.current-year")
    int currentYear;
    @ConfigProperty(name = "my-anime-list-integration.season-limit")
    int limit;

    @Override
    @CacheResult(cacheName = "my-anime-list-integration-season-rest-call")
    public Set<IntegrationNameIdData> getAllAnimes() {
        Set<IntegrationNameIdData> result = new HashSet<>();

        IntegrationAnimeNameListResponse current = integrationAnimeRestClient.getSeasonAnime(currentYear, currentSeason, limit);
        IntegrationAnimeNameListResponse next = integrationAnimeRestClient.getSeasonAnime(currentSeason.nextYear(currentYear), currentSeason.nextSeason(), limit);
        IntegrationAnimeNameListResponse previous = integrationAnimeRestClient.getSeasonAnime(currentSeason.previousYear(currentYear), currentSeason.previousSeason(), limit);

        result.addAll(parseData(current));
        result.addAll(parseData(next));
        result.addAll(parseData(previous));

        return result;
    }

    private List<IntegrationNameIdData> parseData(IntegrationAnimeNameListResponse response) {
        return response.data().stream().map(IntegrationAnimeNameListResponse.Data::node).map(node -> new IntegrationNameIdData(new IntegrationAnimeId(String.valueOf(node.id())), Collections.singletonList(node.title()))).toList();
    }
}
