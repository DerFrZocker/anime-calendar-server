package de.derfrzocker.anime.calendar.integration.mongodb;

import de.derfrzocker.anime.calendar.server.core.api.integration.IntegrationAnimeDao;
import de.derfrzocker.anime.calendar.server.model.core.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.IntegrationId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
class MongoDBIntegrationAnimeDao implements IntegrationAnimeDao {

    @Inject
    MongoDBIntegrationAnimeRepository repository;

    @Override
    public Set<AnimeId> getAnimeIds(IntegrationId integrationId, Set<IntegrationAnimeId> integrationAnimeIds) {
        Map<IntegrationAnimeId, AnimeId> integrationAnimeIdDOS = new HashMap<>();
        repository.getAll(integrationId).forEach(integrationAnimeId -> integrationAnimeIdDOS.put(integrationAnimeId.integrationAnimeId, integrationAnimeId.animeId));
        return integrationAnimeIds.stream().map(integrationAnimeIdDOS::get).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public Set<IntegrationAnimeId> getIntegrationIds(IntegrationId integrationId, AnimeId animeId) {
        return repository.get(animeId).stream().filter(d -> d.integrationId.equals(integrationId)).map(integrationAnimeIdDO -> integrationAnimeIdDO.integrationAnimeId).collect(Collectors.toSet());
    }

    @Override
    public void saveOrMerge(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId, AnimeId animeId) {
        // TODO 2024-09-23: Implement merging
        IntegrationAnimeIdDO integrationAnimeIdDO = new IntegrationAnimeIdDO();
        integrationAnimeIdDO.integrationAnimeId = integrationAnimeId;
        integrationAnimeIdDO.integrationId = integrationId;
        integrationAnimeIdDO.animeId = animeId;

        repository.persist(integrationAnimeIdDO);
    }
}
