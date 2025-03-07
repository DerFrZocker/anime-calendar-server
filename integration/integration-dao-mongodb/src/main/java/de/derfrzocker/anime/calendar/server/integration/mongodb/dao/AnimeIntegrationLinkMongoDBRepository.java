package de.derfrzocker.anime.calendar.server.integration.mongodb.dao;

import de.derfrzocker.anime.calendar.server.integration.mongodb.data.AnimeIntegrationLinkDO;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnimeIntegrationLinkMongoDBRepository implements PanacheMongoRepository<AnimeIntegrationLinkDO> {

}
