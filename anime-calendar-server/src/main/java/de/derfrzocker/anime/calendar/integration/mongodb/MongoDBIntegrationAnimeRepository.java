package de.derfrzocker.anime.calendar.integration.mongodb;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
class MongoDBIntegrationAnimeRepository implements PanacheMongoRepository<IntegrationAnimeIdDO> {

    public Set<IntegrationAnimeIdDO> getAll(IntegrationId integrationId) {
        return new HashSet<>(find("integrationId", integrationId.raw()).list());
    }

    public Set<IntegrationAnimeIdDO> get(AnimeId animeId) {
        return new HashSet<>(find("animeId", animeId.raw()).list());
    }
}
