package de.derfrzocker.anime.calendar.server.mongodb.dao.integration;

import de.derfrzocker.anime.calendar.server.mongodb.data.integration.AnimeIntegrationLinkDO;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeIntegrationLinkMongoDBRepository implements PanacheMongoRepository<AnimeIntegrationLinkDO> {

}
