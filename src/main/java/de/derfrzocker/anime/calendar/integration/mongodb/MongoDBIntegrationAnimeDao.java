package de.derfrzocker.anime.calendar.integration.mongodb;

import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeDao;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationId;
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
}
